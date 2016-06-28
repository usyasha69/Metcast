package com.example.pk.metcast;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.pk.metcast.adapters.MyFragmentPagerAdapter;
import com.example.pk.metcast.fragments.FragmentOne;
import com.example.pk.metcast.fragments.FragmentThree;
import com.example.pk.metcast.fragments.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements LocationListener, ViewPager.OnPageChangeListener, GetQueryTask.RequestResultCallback {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;

    private final List<Fragment> fragments = new ArrayList<Fragment>();

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    public LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments.add(FRAGMENT_ONE, FragmentOne.newInstance());
        fragments.add(FRAGMENT_TWO, FragmentTwo.newInstance());
        fragments.add(FRAGMENT_THREE, FragmentThree.newInstance());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
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

        WeatherModel weatherModel = new WeatherParsing().parseQuery(result);

        int cityId = weatherModel.getCity().getCityId();
        System.out.println("cityId " + cityId);

        String cityName = weatherModel.getCity().getCityName();
        System.out.println("cityName " + cityName);

        double cityLonCoord = weatherModel.getCity().getCoord().getCityLonCoord();
        System.out.println("cityLonCoord " + cityLonCoord);

        double cityLatCoord = weatherModel.getCity().getCoord().getCityLatCoord();
        System.out.println("cityLatCoord " + cityLatCoord);

        String cityCountry = weatherModel.getCity().getCityCountry();
        System.out.println("cityCountry " + cityCountry);

        int cityPopulaton = weatherModel.getCity().getCityPopulation();
        System.out.println("cityPopulation " + cityPopulaton);

        int citySysPopulation = weatherModel.getCity().getSys().getCitySysPopulation();
        System.out.println("citySysPopulation " + citySysPopulation);

        String cod = weatherModel.getCod();
        System.out.println("cod " + cod);

        double message = weatherModel.getMessage();
        System.out.println("message " + message);

        int cnt = weatherModel.getCnt();
        System.out.println("cnt " + cnt);

        int listDt = weatherModel.getListItem().getListDt();
        System.out.println("listDt " + listDt);


    }
}
