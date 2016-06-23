package com.example.pk.metcast.adapters;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.pk.metcast.R;
import com.example.pk.metcast.fragments.FragmentOne;
import com.example.pk.metcast.fragments.FragmentThree;
import com.example.pk.metcast.fragments.FragmentTwo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;

    public static final int FRAGMENTS = 3;

    private static final List<Fragment> fragments = new ArrayList<Fragment>();

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
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Fragment fragmentOne = getSupportFragmentManager().findFragmentById(R.id.fragmentOne);
                TextView tvFragment1 = (TextView) fragmentOne.getView().findViewById(R.id.fragmentOneTv);
                tvFragment1.setText("lat = " + String.valueOf(location.getLatitude()) + " lon = " + String.valueOf(location.getLongitude()));

                String query  = "api.openweathermap.org/data/2.5/forecast?";
                query += ("lat=" + String.valueOf(location.getLatitude()) + "&" + "lan=" + String.valueOf(location.getLongitude()));

                try {
                    URL url = new URL(query);
                    url.openConnection();
                } catch (Exception ex) {
                }
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return FRAGMENTS;
        }
    }
}
