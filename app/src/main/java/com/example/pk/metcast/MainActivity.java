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
import com.example.pk.metcast.fragments.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements LocationListener, ViewPager.OnPageChangeListener, GetQueryTask.RequestResultCallback {

    public static final int WEATHER_FRAGMENT = 0;


    private final List<Fragment> fragments = new ArrayList<Fragment>();

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    public LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments.add(WEATHER_FRAGMENT, WeatherFragment.newInstance());

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

        WeatherParsingModel weatherParsingModel = new WeatherParsing().parseQuery(result);

        //cityId
        int cityId = weatherParsingModel.getCity().getCityId();
        System.out.println("cityId " + cityId);

        //cityName
        String cityName = weatherParsingModel.getCity().getCityName();
        System.out.println("cityName " + cityName);

        //cityLonCoord
        double cityLonCoord = weatherParsingModel.getCity().getCoord().getCityLonCoord();
        System.out.println("cityLonCoord " + cityLonCoord);

        //cityLatCoord
        double cityLatCoord = weatherParsingModel.getCity().getCoord().getCityLatCoord();
        System.out.println("cityLatCoord " + cityLatCoord);

        //cityCountry
        String cityCountry = weatherParsingModel.getCity().getCityCountry();
        System.out.println("cityCountry " + cityCountry);

        //cityPopulaton
        int cityPopulaton = weatherParsingModel.getCity().getCityPopulation();
        System.out.println("cityPopulation " + cityPopulaton);

        //citySysPopulation
        int citySysPopulation = weatherParsingModel.getCity().getSys().getCitySysPopulation();
        System.out.println("citySysPopulation " + citySysPopulation);

        //cod
        String cod = weatherParsingModel.getCod();
        System.out.println("cod " + cod);

        //message
        double message = weatherParsingModel.getMessage();
        System.out.println("message " + message);

        //cnt
        int cnt = weatherParsingModel.getCnt();
        System.out.println("cnt " + cnt);

        //listDt
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            long listDt = weatherParsingModel.getList().get(i).getListDt();
            System.out.println("listDt " + listDt);
        }

        //listMainTemp
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listMainTemp = weatherParsingModel.getList().get(i).getMain().getListMainTemp();
            System.out.println("listMainTemp " + listMainTemp);
        }

        //listMainTempMin
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listMainTempMin = weatherParsingModel.getList().get(i).getMain().getListMainTempMin();
            System.out.println("listMainTempMin " + listMainTempMin);
        }

        //listMainTempMax
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listMainTempMax = weatherParsingModel.getList().get(i).getMain().getListMainTempMax();
            System.out.println("listMainTempMax " + listMainTempMax);
        }

        //listMainPressure
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listMainPressure = weatherParsingModel.getList().get(i).getMain().getListMainPressure();
            System.out.println("listMainPressure " + listMainPressure);
        }

        //listMainSeaLevel
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listMainSeaLevel = weatherParsingModel.getList().get(i).getMain().getListMainSeaLevel();
            System.out.println("listMainSeaLevel " + listMainSeaLevel);
        }

        //listMainGrndLevel
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listMainGrndLevel = weatherParsingModel.getList().get(i).getMain().getListMainGrndLevel();
            System.out.println("listMainGrndLevel " + listMainGrndLevel);
        }

        //listMainHumidity
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            int listMainHumidity = weatherParsingModel.getList().get(i).getMain().getListMainHumidity();
            System.out.println("listMainHumidity " + listMainHumidity);
        }

        //listMainTempKf
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listMainTempKf = weatherParsingModel.getList().get(i).getMain().getListMainTempKf();
            System.out.println("listMainTempKf " + listMainTempKf);
        }

        //listWeatherId
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                int listWeatherId = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherId();
                System.out.println("listWeatherId " + listWeatherId);
            }
        }


        //listWeatherMain
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                String listWeatherMain = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherMain();
                System.out.println("listWeatherMain " + listWeatherMain);
            }
        }

        //listWeatherDescription
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                String listWeatherDescription = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherDescription();
                System.out.println("listWeatherDescription " + listWeatherDescription);
            }
        }

        //listWeatherIcon
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                String listWeatherIcon = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherIcon();
                System.out.println("listWeatherIcon " + listWeatherIcon);
            }
        }

        //listCloudsAll
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listCloudsAll = weatherParsingModel.getList().get(i).getClouds().getListCloudsAll();
            System.out.println("listCloudsAll " + listCloudsAll);
        }

        //listWindSpeed
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listWindSpeed = weatherParsingModel.getList().get(i).getWind().getListWindSpeed();
            System.out.println("listWindSpeed " + listWindSpeed);
        }

        //listWindDeg
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listWindDeg = weatherParsingModel.getList().get(i).getWind().getListWindDeg();
            System.out.println("listWindDeg " + listWindDeg);
        }

        //listRain3h
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            double listRain3h = weatherParsingModel.getList().get(i).getRain().getListRain3h();
            System.out.println("listRain3h " + listRain3h);
        }

        //listSysPod
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            String listSysPod = weatherParsingModel.getList().get(i).getSys().getListSysPod();
            System.out.println("listSysPod " + listSysPod);
        }

        //listDtTxt
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            String listDtTxt = weatherParsingModel.getList().get(i).getListDtTxt();
            System.out.println("listDtTxt " + listDtTxt);
        }
    }
}
