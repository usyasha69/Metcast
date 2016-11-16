package com.example.pk.metcast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.pk.metcast.adapters.MetcastFragmentStatePagerAdapter;
import com.example.pk.metcast.adapters.NavigationDrawerListViewAdapter;
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

    //loaders constants
    private final int LOADER_READ_FROM_DATABASE_ID = 1;
    private final int LOADER_INSERT_TO_DATABASE_ID = 2;
    private final int LOADER_UPDATE_DATABASE_ID = 3;
    private final int LOADER_CHECKED_EMPTY_DB_ID = 4;

    //View Pager and Pager Adapter
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    //Array List with day weather models
    ArrayList<DayWeatherModel> weatherList;

    //for location
    private Location currentLocation;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    //Progress Dialog
    private ProgressDialog progressDialog;

    //Navigation drawer
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //Toolbar
    private Toolbar toolbar;

    /**
     * Private inserted class implements ListView.OnItemClickListener
     * and machining, pressing on element in Navigation Drawer List View.
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectItem(i);
        }
    }

    /**
     * This method set current page in ViewPager and
     * close navigation background drawer.
     *
     * @param position - current item in ListView
     */
    private void selectItem(int position) {
        drawerList.setItemChecked(position, true);
        viewPager.setCurrentItem(position, true);
        drawerLayout.closeDrawer(drawerList);
    }

    //activity lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        createProgressDialog();
        createToolbar();
        configureNavigationDrawer();
        createViewPager();
        createGoogleApiClient();

        //checked is db empty
        getSupportLoaderManager().initLoader(LOADER_CHECKED_EMPTY_DB_ID, null, this).forceLoad();
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * This method create toolbar.
     */
    protected void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * This method create and configure progress dialog.
     */
    protected void createProgressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.setMessage(getString(R.string.progress_dialog_reading));
    }

    /**
     * This method create and configure navigation drawer.
     */
    protected void configureNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar
                , R.string.drawer_open, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    /**
     * This method initialize days array and
     * filling List View in navigation drawer
     * data.
     */
    private void fillDrawerData() {
        ArrayList<String> days = new ArrayList<>();

        for (int i = 0; i < weatherList.size(); i++) {
            days.add(weatherList.get(i).getDay());
        }

        drawerList.setAdapter(new NavigationDrawerListViewAdapter(
                this, days));
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
                mLoader = new InsertToDBLoader(this, weatherList);
                break;
            case LOADER_UPDATE_DATABASE_ID:
                mLoader = new UpdateDBLoader(this, weatherList);
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
                weatherList = new DBWorker().readDataFromBD((Cursor) data);

                pagerAdapter = new MetcastFragmentStatePagerAdapter(getSupportFragmentManager()
                        , weatherList);
                viewPager.setAdapter(pagerAdapter);

                //filling List View in Navigation Drawer data
                fillDrawerData();

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
                    progressDialog.show();
                    getSupportLoaderManager().initLoader(LOADER_READ_FROM_DATABASE_ID
                            , null, this).forceLoad();
                } else {
                    Toast toast = Toast.makeText(this, R.string.toast_enable_gps
                            , Toast.LENGTH_LONG);

                    toast.setGravity(Gravity.CENTER, 0, 0);

                    LinearLayout toastContainer = (LinearLayout) toast.getView();
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_my_location_white_48dp));
                    toastContainer.addView(imageView, 0);

                    toast.setView(toastContainer);
                    toast.show();
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
        weatherList = new ConverterToWeather().group(weatherParsingModel);

        pagerAdapter = new MetcastFragmentStatePagerAdapter(getSupportFragmentManager()
                , weatherList);
        viewPager.setAdapter(pagerAdapter);

        //fill List View in Navigation Drawer data
        fillDrawerData();

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

    /**
     * This method checked internet connection.
     *
     * @return - is connected
     */
    private boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    //GoogleApiClient implements methods
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        createLocationRequest();
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
        final int LOCATION_INTERVAL_UPDATE_TIME = 40 * 1000;
        final int LOCATION_FAST_INTERVAL_UPDATE_TIME = 35 * 1000;
        final int LOCATION_UPDATE_SMALLEST_DISPLACEMENT = 100;

        locationRequest.setInterval(LOCATION_INTERVAL_UPDATE_TIME);
        locationRequest.setSmallestDisplacement(LOCATION_UPDATE_SMALLEST_DISPLACEMENT);
        locationRequest.setFastestInterval(LOCATION_FAST_INTERVAL_UPDATE_TIME);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //LocationListener implement method
    @Override
    public void onLocationChanged(Location location) {
        progressDialog.setMessage(getString(R.string.progress_dialog_updating));

        currentLocation = location;
        if (isOnline()) {
            progressDialog.show();
            useRetrofit();
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.gps_menu:
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                break;
            case R.id.update_weather:
                if (currentLocation != null) {
                    if (isOnline()) {
                        progressDialog.setMessage(getString(R.string.progress_dialog_updating));
                        progressDialog.show();
                        useRetrofit();
                    } else {
                        Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.toast_enable_gps), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
