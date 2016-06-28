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

        //cityId
        int cityId = weatherModel.getCity().getCityId();
        System.out.println("cityId " + cityId);

        //cityName
        String cityName = weatherModel.getCity().getCityName();
        System.out.println("cityName " + cityName);

        //cityLonCoord
        double cityLonCoord = weatherModel.getCity().getCoord().getCityLonCoord();
        System.out.println("cityLonCoord " + cityLonCoord);

        //cityLatCoord
        double cityLatCoord = weatherModel.getCity().getCoord().getCityLatCoord();
        System.out.println("cityLatCoord " + cityLatCoord);

        //cityCountry
        String cityCountry = weatherModel.getCity().getCityCountry();
        System.out.println("cityCountry " + cityCountry);

        //cityPopulaton
        int cityPopulaton = weatherModel.getCity().getCityPopulation();
        System.out.println("cityPopulation " + cityPopulaton);

        //citySysPopulation
        int citySysPopulation = weatherModel.getCity().getSys().getCitySysPopulation();
        System.out.println("citySysPopulation " + citySysPopulation);

        //cod
        String cod = weatherModel.getCod();
        System.out.println("cod " + cod);

        //message
        double message = weatherModel.getMessage();
        System.out.println("message " + message);

        //cnt
        int cnt = weatherModel.getCnt();
        System.out.println("cnt " + cnt);

        //listDt
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            long listDt = weatherModel.getList().get(i).getListDt();
            System.out.println("listDt " + listDt);
        }

        //listMainTemp
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listMainTemp = weatherModel.getList().get(i).getMain().getListMainTemp();
            System.out.println("listMainTemp " + listMainTemp);
        }

        //listMainTempMin
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listMainTempMin = weatherModel.getList().get(i).getMain().getListMainTempMin();
            System.out.println("listMainTempMin " + listMainTempMin);
        }

        //listMainTempMax
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listMainTempMax = weatherModel.getList().get(i).getMain().getListMainTempMax();
            System.out.println("listMainTempMax " + listMainTempMax);
        }

        //listMainPressure
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listMainPressure = weatherModel.getList().get(i).getMain().getListMainPressure();
            System.out.println("listMainPressure " + listMainPressure);
        }

        //listMainSeaLevel
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listMainSeaLevel = weatherModel.getList().get(i).getMain().getListMainSeaLevel();
            System.out.println("listMainSeaLevel " + listMainSeaLevel);
        }

        //listMainGrndLevel
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listMainGrndLevel = weatherModel.getList().get(i).getMain().getListMainGrndLevel();
            System.out.println("listMainGrndLevel " + listMainGrndLevel);
        }

        //listMainHumidity
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            int listMainHumidity = weatherModel.getList().get(i).getMain().getListMainHumidity();
            System.out.println("listMainHumidity " + listMainHumidity);
        }

        //listMainTempKf
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listMainTempKf = weatherModel.getList().get(i).getMain().getListMainTempKf();
            System.out.println("listMainTempKf " + listMainTempKf);
        }

        //listWeatherId
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            for (int j = 0; j < weatherModel.getList().get(i).getWeather().size(); j++) {
                int listWeatherId = weatherModel.getList().get(i).getWeather().get(j).getListWeatherId();
                System.out.println("listWeatherId " + listWeatherId);
            }
        }


        //listWeatherMain
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            for (int j = 0; j < weatherModel.getList().get(i).getWeather().size(); j++) {
                String listWeatherMain = weatherModel.getList().get(i).getWeather().get(j).getListWeatherMain();
                System.out.println("listWeatherMain " + listWeatherMain);
            }
        }

        //listWeatherDescription
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            for (int j = 0; j < weatherModel.getList().get(i).getWeather().size(); j++) {
                String listWeatherDescription = weatherModel.getList().get(i).getWeather().get(j).getListWeatherDescription();
                System.out.println("listWeatherDescription " + listWeatherDescription);
            }
        }

        //listWeatherIcon
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            for (int j = 0; j < weatherModel.getList().get(i).getWeather().size(); j++) {
                String listWeatherIcon = weatherModel.getList().get(i).getWeather().get(j).getListWeatherIcon();
                System.out.println("listWeatherIcon " + listWeatherIcon);
            }
        }

        //listCloundsAll
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listCloundsAll = weatherModel.getList().get(i).getClouds().getListCloudsAll();
            System.out.println("listCloundsAll " + listCloundsAll);
        }

        //listWindSpeed
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listWindSpeed = weatherModel.getList().get(i).getWind().getListWindSpeed();
            System.out.println("listWindSpeed " + listWindSpeed);
        }

        //listWindDeg
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listWindDeg = weatherModel.getList().get(i).getWind().getListWindDeg();
            System.out.println("listWindDeg " + listWindDeg);
        }

        //listRain3h
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            double listRain3h = weatherModel.getList().get(i).getRain().getListRain3h();
            System.out.println("listRain3h " + listRain3h);
        }

        //listSysPod
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            String listSysPod = weatherModel.getList().get(i).getSys().getListSysPod();
            System.out.println("listSysPod " + listSysPod);
        }

        //listDtTxt
        for (int i = 0; i < weatherModel.getList().size(); i++) {
            String listDtTxt = weatherModel.getList().get(i).getListDtTxt();
            System.out.println("listDtTxt " + listDtTxt);
        }
    }
}
