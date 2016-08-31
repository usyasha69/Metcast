package com.example.pk.metcast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.pk.metcast.adapters.MyFragmentStatePagerAdapter;
import com.example.pk.metcast.database.DBWorker;
import com.example.pk.metcast.database.MetcastProvider;
import com.example.pk.metcast.loaders.EmptyCheckDBLoader;
import com.example.pk.metcast.loaders.InsertToDBLoader;
import com.example.pk.metcast.loaders.UpdateDBLoader;
import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherParsingModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetcastActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener
        , LoaderManager.LoaderCallbacks<Object>
        , Callback<WeatherParsingModel>, GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener
        , LocationListener {

    //view pager and pager adapter
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    //loaders constants
    private final int LOADER_READ_FROM_DATABASE_ID = 1;
    private final int LOADER_INSERT_TO_DATABASE_ID = 2;
    private final int LOADER_UPDATE_DATABASE_ID = 3;
    private final int LOADER_CHECKED_EMPTY_DB_ID = 4;

    //array list with day weather models
    ArrayList<DayWeatherModel> readingDBList;
    ArrayList<DayWeatherModel> updateWeatherList;

    //location
    private Location currentLocation;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    //progress dialog
    private ProgressDialog progressDialog;



    //activity lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createProgressDialog();
        createViewPager();

        getSupportLoaderManager().initLoader(LOADER_CHECKED_EMPTY_DB_ID, null, this).forceLoad();

        createGoogleApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    protected void createProgressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.setMessage(getString(R.string.progress_dialog_reading));

        progressDialog.show();
    }

    //onPageChangeListener implements methods
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * This method create view pager.
     */
    protected void createViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
    }

    //LoaderCallback implements methods
    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        Loader mLoader = null;
        switch (id) {
            case LOADER_READ_FROM_DATABASE_ID:
                mLoader = new CursorLoader(this, MetcastProvider.METCAST_CONTENT_URI
                        , null, null, null, null);
                break;
            case LOADER_INSERT_TO_DATABASE_ID:
                mLoader = new InsertToDBLoader(this, updateWeatherList);
                break;
            case LOADER_UPDATE_DATABASE_ID:
                mLoader = new UpdateDBLoader(this, updateWeatherList);
                break;
            case LOADER_CHECKED_EMPTY_DB_ID:
                mLoader = new EmptyCheckDBLoader(this);
                break;
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case LOADER_READ_FROM_DATABASE_ID:
                //read from database
                readingDBList = new DBWorker().readDataFromBD((Cursor) data);
                pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager()
                        , readingDBList);
                viewPager.setAdapter(pagerAdapter);

                progressDialog.dismiss();
                break;
            case LOADER_INSERT_TO_DATABASE_ID:
                break;
            case LOADER_UPDATE_DATABASE_ID:
                //if database empty, insert
                if (!new DBWorker().emptyCheckedDB(this)) {
                    getSupportLoaderManager().initLoader(LOADER_INSERT_TO_DATABASE_ID
                            , null, this).forceLoad();
                }
                break;
            case LOADER_CHECKED_EMPTY_DB_ID:
                //if database does'nt empty, reading from database
                if (((boolean) data)) {
                    getSupportLoaderManager().initLoader(LOADER_READ_FROM_DATABASE_ID
                            , null, this).forceLoad();
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
    }

    //RetrofitCallback methods
    @Override
    public void onResponse(Call<WeatherParsingModel> call, Response<WeatherParsingModel> response) {
        WeatherParsingModel weatherParsingModel = response.body();
        updateWeatherList = new ConverterToWeather().group(weatherParsingModel);

        pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager()
                , updateWeatherList);
        viewPager.setAdapter(pagerAdapter);

        progressDialog.dismiss();

        //update database
        getSupportLoaderManager().initLoader(LOADER_UPDATE_DATABASE_ID, null, this).forceLoad();
    }

    @Override
    public void onFailure(Call<WeatherParsingModel> call, Throwable t) {

    }

    /**
     * This method get coordinates and
     * use retrofit.
     */
    private void useRetrofit() {
        String baseURL = "http://api.openweathermap.org/data/2.5/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        Call<WeatherParsingModel> call =
                weatherAPI.loadQuestions(String.valueOf(currentLocation.getLatitude())
                        , String.valueOf(currentLocation.getLongitude())
                        , "4c898f591f4e595efcdd5db855f26762");

        call.enqueue(this);
    }

    //GoogleApiClient implements methods
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //create location request
        createLocationRequest();

        //start location updates
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * This method create
     * google api client.
     */
    protected void createGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This method create location
     * request and set the interval of
     * getting gps coordinates.
     */
    protected void createLocationRequest() {
        locationRequest = new LocationRequest();

        //location update times
        final int LOCATION_INTERVAL_UPDATE_TIME = 10 * 1000;
        final int LOCATION_FAST_INTERVAL_UPDATE_TIME = 15 * 1000;
        final int LOCATION_UPDATE_SMALLEST_DISPLACEMENT = 20;

        locationRequest.setInterval(LOCATION_INTERVAL_UPDATE_TIME);
        locationRequest.setSmallestDisplacement(LOCATION_UPDATE_SMALLEST_DISPLACEMENT);
        locationRequest.setFastestInterval(LOCATION_FAST_INTERVAL_UPDATE_TIME);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //LocationListener implement method
    @Override
    public void onLocationChanged(Location location) {
        progressDialog.setMessage(getString(R.string.progress_dialog_updating));
        progressDialog.show();

        currentLocation = location;
        useRetrofit();
    }

    /**
     * This method stating location
     * updates.
     */
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    /**
     * This method stopped location
     * updates.
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    //implements methods with configuration toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_metcast, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gps_menu:
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                break;
            case R.id.update_weather:
                if (currentLocation != null) {
                    progressDialog.show();
                    useRetrofit();
                } else {
                    Toast.makeText(this, getString(R.string.toast_gps_on), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
