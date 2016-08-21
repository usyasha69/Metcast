package com.example.pk.metcast;

import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

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

public class MetcastActivity extends FragmentActivity implements ViewPager.OnPageChangeListener
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
    ArrayList<DayWeatherModel> list;

    //location
    private Location mLocation;
    private GoogleApiClient googleApiClient;

    //activity lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        //create view pager
        createViewPager();

        //create google api client
        createGoogleApiClient();

        //checked, database empty or not
        getSupportLoaderManager().initLoader(LOADER_CHECKED_EMPTY_DB_ID, null, this).forceLoad();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
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
     * This method create view pager
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
                mLoader = new InsertToDBLoader(this, list);
                break;
            case LOADER_UPDATE_DATABASE_ID:
                mLoader = new UpdateDBLoader(this, list);
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
                list = new DBWorker().readDataFromBD((Cursor) data);
                pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), list);
                viewPager.setAdapter(pagerAdapter);
                break;
            case LOADER_INSERT_TO_DATABASE_ID:
                break;
            case LOADER_UPDATE_DATABASE_ID:
                //if database empty, insert
                if ((int) data == 0) {
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
        list = new ConverterToWeather().group(weatherParsingModel);

        pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(pagerAdapter);
        //update database
        getSupportLoaderManager().initLoader(LOADER_UPDATE_DATABASE_ID, null, this).forceLoad();
    }

    @Override
    public void onFailure(Call<WeatherParsingModel> call, Throwable t) {

    }

    /**
     * This method get coordinates and
     * use retrofit
     */
    private void useRetrofit() {
        String baseURL = "http://api.openweathermap.org/data/2.5/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        Call<WeatherParsingModel> call =
                weatherAPI.loadQuestions(String.valueOf(mLocation.getLatitude())
                        , String.valueOf(mLocation.getLongitude())
                        , "4c898f591f4e595efcdd5db855f26762");

        call.enqueue(this);
    }

    //GoogleApiClient implements methods
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //create location request
        createLocationRequest();

        //mLocation = LocationServices.FusedLocationApi
                //.getLastLocation(googleApiClient);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * This method create
     * google api client
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
        LocationRequest locationRequest = new LocationRequest();

        //location update times
        final int LOCATION_INTERVAL_UPDATE_TIME = 10 * 1000;
        final int LOCATION_FAST_INTERVAL_UPDATE_TIME = 5 * 1000;
        final int LOCATION_UPDATE_SMALLEST_DISPLACEMENT = 3;

        locationRequest.setInterval(LOCATION_INTERVAL_UPDATE_TIME);
        locationRequest.setSmallestDisplacement(LOCATION_UPDATE_SMALLEST_DISPLACEMENT);
        locationRequest.setFastestInterval(LOCATION_FAST_INTERVAL_UPDATE_TIME);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    //LocationListener implement method
    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        useRetrofit();
    }
}
