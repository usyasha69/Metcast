package com.example.pk.metcast.adapters;

import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pk.metcast.MainActivity;
import com.example.pk.metcast.fragments.WeatherFragment;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private int PAGE_COUNT = 5;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return WeatherFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "Fragment " + position;
    }
}
