package com.example.pk.metcast;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

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


            int cityId = weatherModel.getCityId();
            System.out.println("cityId " + cityId);
            String cityName = weatherModel.getCityName();
            System.out.println("cityName " + cityName);
            double cityLonCoord = weatherModel.getCityLonCoord();
            System.out.println("cityLonCoord " + cityLonCoord);
            double cityLatCoord = weatherModel.getCityLatCoord();
            System.out.println("cityLatCoord " + cityLatCoord);
            String country = weatherModel.getCountry();
            System.out.println("country " + country);
            String cod = weatherModel.getCod();
            System.out.println("cod " + cod);
            double message = weatherModel.getMessage();
            System.out.println("message " + message);
            int cnt = weatherModel.getCnt();
            System.out.println("cnt " + cnt);
            int listDt = weatherModel.getListDt();
            System.out.println("listDt " + listDt);
            double listMainTemp = weatherModel.getListMainTemp();
            System.out.println("listMainTemp " + listMainTemp);
            double listMainTemp_min = weatherModel.getListMainTemp_min();
            System.out.println("listMainTemp_min " + listMainTemp_min);
            double listMainTemp_max = weatherModel.getListMainTemp_max();
            System.out.println("listMainTemp_max " + listMainTemp_max);
            double listMainPressure = weatherModel.getListMainPressure();
            System.out.println("listMainPressure " + listMainPressure);
            double listMainSea_level = weatherModel.getListMainSea_level();
            System.out.println("listMainSea_level " + listMainSea_level);
            double listMainGrnd_level = weatherModel.getListMainGrnd_level();
            System.out.println("listMainGrnd_level " + listMainGrnd_level);
            int listMainHumidity = weatherModel.getListMainHumidity();
            System.out.println("listMainHumidity " + listMainHumidity);
            double listMainTemp_kf = weatherModel.getListMainTemp_kf();
            System.out.println("listMainTemp_kf " + listMainTemp_kf);
            int listWeatherId = weatherModel.getListWeatherId();
            System.out.println("listWeatherId " + listWeatherId);
            String listWeatherMain = weatherModel.getListWeatherMain();
            System.out.println("listWeatherMain " + listWeatherMain);
            String listWeatherDescription = weatherModel.getListWeatherDescription();
            System.out.println("listWeatherDescription " + listWeatherDescription);
            String listWeatherIcon = weatherModel.getListWeatherIcon();
            System.out.println("listWeatherIcon " + listWeatherIcon);
            int listCloudsAll = weatherModel.getListCloudsAll();
            System.out.println("listCloudsAll " + listCloudsAll);
            double listWindSpeed = weatherModel.getListWindSpeed();
            System.out.println("listWindSpeed " + listWindSpeed);
            double listWindDeg = weatherModel.getListWindDeg();
            System.out.println("listWindDeg " + listWindDeg);
            String listSysPod = weatherModel.getListSysPod();
            System.out.println("listSysPod " + listSysPod);
            String listDt_txt = weatherModel.getListDtTxt();
            System.out.println("listDt_txt " + listDt_txt);
    }
}
