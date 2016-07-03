package com.example.pk.metcast;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.pk.metcast.adapters.MyFragmentPagerAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LocationListener, ViewPager.OnPageChangeListener, GetQueryTask.RequestResultCallback {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    public LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
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
            new GetQueryTask(location, this).execute();
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

        System.out.println(result);

        WeatherParsingModel weatherParsingModel = new WeatherParsing().parseQuery(result);
        ArrayList<DayWeatherModel>  list = new ConversionToWeather().group(weatherParsingModel);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getDay());
            printArray(list, i);
        }
    }

    public void printArray(ArrayList<DayWeatherModel> list, int i) {

        DayWeatherModel dayWeatherModel = list.get(i);
        for (int j = 0; j < dayWeatherModel.getWeathers().size(); j++) {
            System.out.println(dayWeatherModel.getWeathers().get(j).getTime() + " "
                    + dayWeatherModel.getWeathers().get(j).getWeather() + " "
                    + new DecimalFormat("#0.0").format(dayWeatherModel.getWeathers().get(j).getTemperature() - 273.15));
        }
    }
}
