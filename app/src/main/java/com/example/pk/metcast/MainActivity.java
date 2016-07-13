package com.example.pk.metcast;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.pk.metcast.adapters.MyFragmentStatePagerAdapter;
import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherParsingModel;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LocationListener, ViewPager.OnPageChangeListener, LoaderManager.LoaderCallbacks<String> {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private LocationManager locationManager;

    private final int LOADER_GET_QUERY_ID = 1;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);

        WorkWithDB workWithDB = new WorkWithDB();

        if (!workWithDB.dbEmpty(this)) {
            pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), workWithDB.readDataFromBD(this));
            viewPager.setAdapter(pagerAdapter);
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
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d("mytag", "onCreate");
        Loader<String> mLoader = null;
        switch (id){
            case LOADER_GET_QUERY_ID:
            mLoader = new GetQueryTaskLoader(this, location);
                break;
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (loader.getId()) {
            case LOADER_GET_QUERY_ID:
                WeatherParsingModel weatherParsingModel = new WeatherParsing().parseQuery(data);
                ArrayList<DayWeatherModel> list = new ConversionToWeather().group(weatherParsingModel);

                WorkWithDB workWithDB = new WorkWithDB();

                pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), list);

                if (workWithDB.dbEmpty(this)) {
                    workWithDB.storingDataOnTheDB(this, list);
                } else {
                    workWithDB.updateDB(this, list);
                }

                viewPager.setAdapter(pagerAdapter);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
