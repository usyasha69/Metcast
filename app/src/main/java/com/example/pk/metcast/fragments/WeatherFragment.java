package com.example.pk.metcast.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.metcast.DayWeatherModel;
import com.example.pk.metcast.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherFragment extends Fragment {

    private static final String WEATHER_KEY = "weatherKey";
    private static String fragmentWeather;

    public static WeatherFragment newInstance(ArrayList<DayWeatherModel> list, int position) {

        Bundle args = new Bundle();

        WeatherFragment fragment = new WeatherFragment();

        String dayWeather = "";
        for (int i = 0; i < list.get(position).getWeathers().size(); i++) {
            dayWeather += list.get(position).getWeathers().get(i).getTime() + " "
                    + list.get(position).getWeathers().get(i).getWeather() + " "
                    + new DecimalFormat("#0.0").format(list.get(position).getWeathers().get(i).getTemperature() - 273.15)
                    + "\n" + "\n";
        }

        args.putString(WEATHER_KEY, dayWeather);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentWeather = getArguments().getString(WEATHER_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weatherfragment, container, false);
        View tv = v.findViewById(R.id.weatherFragment);
        ((TextView)tv).setText(fragmentWeather);
        return v;
    }

}
