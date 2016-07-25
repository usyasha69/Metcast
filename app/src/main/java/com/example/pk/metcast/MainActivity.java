package com.example.pk.metcast;

import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.pk.metcast.adapters.MyFragmentStatePagerAdapter;
import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherParsingModel;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LocationListener, ViewPager.OnPageChangeListener, LoaderManager.LoaderCallbacks<Object> {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private LocationManager locationManager;
    private Location location;

    private final int LOADER_GET_QUERY_ID = 1;
    private final int LOADER_READ_FROM_DATABASE_ID = 2;
    private final int LOADER_INSERT_TO_DATABASE_ID = 3;
    private final int LOADER_UPDATE_DATABASE_ID = 4;


    ArrayList<DayWeatherModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);

        WorkWithDB workWithDB = new WorkWithDB();

        if (!workWithDB.dbEmpty(this)) {
            getSupportLoaderManager().initLoader(LOADER_READ_FROM_DATABASE_ID, null, this).forceLoad();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, this);
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
            getSupportLoaderManager().initLoader(LOADER_GET_QUERY_ID, null, this).forceLoad();
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
            case LOADER_GET_QUERY_ID:
                mLoader = new GetQueryTaskLoader(this, location);
                break;
            case LOADER_READ_FROM_DATABASE_ID:
                mLoader = new CursorLoader(this) {
                    @Override
                    public Cursor loadInBackground() {
                        list  = new WorkWithDB().readDataFromBD(getContext());
                        return null;
                    }
                };
                break;
            case LOADER_INSERT_TO_DATABASE_ID:
                mLoader = new CursorLoader(this) {
                    @Override
                    public Cursor loadInBackground() {
                        new WorkWithDB().storingDataOnTheDB(getContext(), list);
                        return null;
                    }
                };
                break;
            case LOADER_UPDATE_DATABASE_ID:
                mLoader = new CursorLoader(this) {
                    @Override
                    public Cursor loadInBackground() {
                        new WorkWithDB().updateDB(getContext(), list);
                        return null;
                    }
                };
                break;
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case LOADER_GET_QUERY_ID:
                WeatherParsingModel weatherParsingModel = new WeatherParsing().parseQuery(String.valueOf(data));
                list = new ConversionToWeather().group(weatherParsingModel);

                pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), list);
                viewPager.setAdapter(pagerAdapter);

                WorkWithDB workWithDB = new WorkWithDB();

                if (workWithDB.dbEmpty(this))
                    getSupportLoaderManager().initLoader(LOADER_INSERT_TO_DATABASE_ID, null, this).forceLoad();
                else
                    getSupportLoaderManager().initLoader(LOADER_UPDATE_DATABASE_ID, null, this).forceLoad();
                break;
            case LOADER_READ_FROM_DATABASE_ID:
                pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), list);
                viewPager.setAdapter(pagerAdapter);
                break;
            case LOADER_INSERT_TO_DATABASE_ID:
                break;
            case LOADER_UPDATE_DATABASE_ID:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
    }
}
