package com.example.pk.metcast.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.pk.metcast.R;
import com.example.pk.metcast.models.DayWeatherModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherFragment extends ListFragment{

    private static final String WEATHER_KEY = "weatherKey";
    private ArrayList<String> fragmentWeather;

    public static WeatherFragment newInstance(DayWeatherModel dayWeatherModel) {

        Bundle args = new Bundle();

        WeatherFragment fragment = new WeatherFragment();

        ArrayList<String> dwmWeather = new ArrayList<>();

        for (int i = 0; i < dayWeatherModel.getWeathers().size(); i++) {
            StringBuilder dayWeather = new StringBuilder();

            dayWeather.append(dayWeatherModel.getWeathers().get(i).getTime());
            dayWeather.append(" ");
            dayWeather.append(dayWeatherModel.getWeathers().get(i).getWeather());
            dayWeather.append(" ");
            dayWeather.append(new DecimalFormat("#0.0").format(dayWeatherModel.getWeathers().get(i).getTemperature() - 273.15));
            dwmWeather.add(dayWeather.toString());
        }

        args.putStringArrayList(WEATHER_KEY, dwmWeather);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentWeather = getArguments().getStringArrayList(WEATHER_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weatherfragment, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, fragmentWeather);
        setListAdapter(adapter);
        return v;
    }

}
