package com.example.pk.metcast.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.metcast.R;
import com.example.pk.metcast.adapters.ViewPagerRecyclerViewAdapter;
import com.example.pk.metcast.models.DayWeatherModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherFragment extends Fragment {

    //key for serializable
    private static final String WEATHER_KEY = "weather";

    //Array Lists with weather
    private ArrayList<String> fragmentDate;
    private ArrayList<String> fragmentWeather;
    private ArrayList<String> fragmentTemp;

    public static WeatherFragment newInstance(DayWeatherModel dayWeatherModel) {

        Bundle args = new Bundle();

        WeatherFragment fragment = new WeatherFragment();

        args.putSerializable(WEATHER_KEY, dayWeatherModel);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        DayWeatherModel dayWeatherModel = (DayWeatherModel) getArguments()
                .getSerializable(WEATHER_KEY);

        fragmentDate = new ArrayList<>();
        fragmentWeather = new ArrayList<>();
        fragmentTemp = new ArrayList<>();

        assert dayWeatherModel != null;
        for (int i = 0; i < dayWeatherModel.getWeathers().size(); i++) {
            fragmentDate.add(dayWeatherModel.getWeathers().get(i).getTime());
            fragmentWeather.add(dayWeatherModel.getWeathers().get(i).getWeather());
            fragmentTemp.add(String.valueOf(
                    new DecimalFormat("#0").format
                            (dayWeatherModel.getWeathers().get(i).getTemperature() - 273.15)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ViewPagerRecyclerViewAdapter(fragmentDate, fragmentWeather, fragmentTemp, getContext()));

        return v;
    }
}
