package com.example.pk.metcast.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.pk.metcast.R;
import com.example.pk.metcast.adapters.RvAdapter;
import com.example.pk.metcast.models.DayWeatherModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherFragment extends Fragment{

    private static final String WEATHER_KEY = "weatherKey";
    private static final String DAY_KEY = "dayKey";
    private ArrayList<String> fragmentWeather;
    private String fragmentDay;

    public static WeatherFragment newInstance(DayWeatherModel dayWeatherModel) {

        Bundle args = new Bundle();

        String day = dayWeatherModel.getDay();

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
        args.putString(DAY_KEY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentWeather = getArguments().getStringArrayList(WEATHER_KEY);
        fragmentDay = getArguments().getString(DAY_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weatherfragment, container, false);

        if (fragmentDay.equals("Tuesday") || fragmentDay.equals("Thursday") || fragmentDay.equals("Saturday")) {
            ListView listView = (ListView) v.findViewById(R.id.fragmentListView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, fragmentWeather);
            listView.setAdapter(adapter);
        } else {
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragmentRecyclerView);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            RvAdapter rvAdapter = new RvAdapter(fragmentWeather);
            recyclerView.setAdapter(rvAdapter);
        }

        return v;
    }
}
