package com.example.pk.metcast;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.pk.metcast.adapters.MyFragmentStatePagerAdapter;
import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherParsingModel;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LocationListener, ViewPager.OnPageChangeListener, GetQueryTask.RequestResultCallback {

    private ViewPager viewPager;

    public LocationManager locationManager;

    GetQueryTask getQueryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
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

            //GET query
            if (getQueryTask == null) {
                getQueryTask = new GetQueryTask(location, this);
                getQueryTask.execute();
            }
            getQueryTask.link(this);
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

    //RequestResultCallback methods

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestFinish(String result) {
        WeatherParsingModel weatherParsingModel = new WeatherParsing().parseQuery(result);
        ArrayList<DayWeatherModel>  list = new ConversionToWeather().group(weatherParsingModel);

        PagerAdapter pagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), list);

        new WorkWithDB().storingDataOnTheDB(this, list);
        viewPager.setAdapter(pagerAdapter);
    }

    //set to zero AsyncTask link
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        getQueryTask.unLink();
        return getQueryTask;
    }
}
