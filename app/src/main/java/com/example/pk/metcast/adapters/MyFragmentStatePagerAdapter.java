package com.example.pk.metcast.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pk.metcast.DayWeatherModel;
import com.example.pk.metcast.fragments.WeatherFragment;

import java.util.ArrayList;


public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<DayWeatherModel> list;

    public MyFragmentStatePagerAdapter(FragmentManager fm, ArrayList<DayWeatherModel> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) { return WeatherFragment.newInstance(list, position);
    }

    @Override
    public int getCount() { return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) { return list.get(position).getDay();
    }
}
