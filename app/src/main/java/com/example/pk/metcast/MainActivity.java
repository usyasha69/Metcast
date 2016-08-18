package com.example.pk.metcast;

import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.crashlytics.android.Crashlytics;
import com.example.pk.metcast.adapters.MyFragmentStatePagerAdapter;
import com.example.pk.metcast.loaders.EmptyCheckDBLoader;
import com.example.pk.metcast.loaders.InsertToDBLoader;
import com.example.pk.metcast.loaders.UpdateDBLoader;
import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherParsingModel;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LocationListener, ViewPager.OnPageChangeListener, LoaderManager.LoaderCallbacks<Object>, Callback<WeatherParsingModel> {

    private ViewPager viewPager;

    private LocationManager locationManager;
    private PagerAdapter pagerAdapter;

    private Location location;

    private final int LOADER_READ_FROM_DATABASE_ID = 1;
    private final int LOADER_INSERT_TO_DATABASE_ID = 2;
    private final int LOADER_UPDATE_DATABASE_ID = 3;
    private final int LOADER_CHECKED_EMPTY_DB_ID = 4;

    ArrayList<DayWeatherModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);

        //checked, database empty?
        getSupportLoaderManager().initLoader(LOADER_CHECKED_EMPTY_DB_ID, null, this).forceLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , 1000 * 30, 50, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
                    , 1000 * 30, 50, this);
        }

        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    //LocationListener implement methods
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.location = location;
            useRetrofit();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        locationManager.getLastKnownLocation(s);
    }

    @Override
    public void onProviderDisabled(String s) {

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

    //LoaderCallback methods
    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        Loader mLoader = null;
        switch (id){
            case LOADER_READ_FROM_DATABASE_ID:
                mLoader = new CursorLoader(this, MetcastProvider.METCAST_CONTENT_URI, null, null, null, null);
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
                    getSupportLoaderManager().initLoader(LOADER_INSERT_TO_DATABASE_ID, null, this).forceLoad();
                }
                break;
            case LOADER_CHECKED_EMPTY_DB_ID:
                //if database does'nt empty, reading from database
                if (((boolean) data)) {
                    getSupportLoaderManager().initLoader(LOADER_READ_FROM_DATABASE_ID, null, this).forceLoad();
            }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
    }

    //retrofit callback methods
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

    //subsidiary method get coordinates and used retrofit
    private void useRetrofit() {
        String baseURL = "http://api.openweathermap.org/data/2.5/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        Call<WeatherParsingModel> call =
                weatherAPI.loadQuestions(String.valueOf(location.getLatitude())
                        , String.valueOf(location.getLongitude())
                        , "4c898f591f4e595efcdd5db855f26762");


        call.enqueue(this);
    }
}
