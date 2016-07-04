package com.example.pk.metcast.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.R;

import java.text.DecimalFormat;

public class WeatherFragment extends Fragment {

    private static final String WEATHER_KEY = "weatherKey";
    private String fragmentWeather;

    public static WeatherFragment newInstance(DayWeatherModel dayWeatherModel) {

        Bundle args = new Bundle();

        WeatherFragment fragment = new WeatherFragment();

        StringBuilder dayWeather = new StringBuilder();
        for (int i = 0; i < dayWeatherModel.getWeathers().size(); i++) {
            dayWeather.append(dayWeatherModel.getWeathers().get(i).getTime());
            dayWeather.append(" ");
            dayWeather.append(dayWeatherModel.getWeathers().get(i).getWeather());
            dayWeather.append(" ");
            dayWeather.append(new DecimalFormat("#0.0").format(dayWeatherModel.getWeathers().get(i).getTemperature() - 273.15));
            dayWeather.append("\n");
            dayWeather.append("\n");
        }

        args.putString(WEATHER_KEY, dayWeather.toString());
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
