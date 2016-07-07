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
import com.example.pk.metcast.adapters.LvAdapter;
import com.example.pk.metcast.adapters.RvAdapter;
import com.example.pk.metcast.models.DayWeatherModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherFragment extends Fragment{

    private static final String DATE_KEY = "dateKey";
    private static final String WEATHER_KEY = "weatherKey";
    private static final String TEMP_KEY = "tempKey";

    private static final String DAY_KEY = "dayKey";

    private ArrayList<String> fragmentDate;
    private ArrayList<String> fragmentWeather;
    private ArrayList<String> fragmentTemp;

    private String fragmentDay;

    public static WeatherFragment newInstance(DayWeatherModel dayWeatherModel) {

        Bundle args = new Bundle();

        WeatherFragment fragment = new WeatherFragment();

        String day = dayWeatherModel.getDay();

        ArrayList<String> dwmDate = new ArrayList<>();
        ArrayList<String> dwmWeather = new ArrayList<>();
        ArrayList<String> dwmTemp = new ArrayList<>();

        for (int i = 0; i < dayWeatherModel.getWeathers().size(); i++) {
            dwmDate.add(dayWeatherModel.getWeathers().get(i).getTime());
            dwmWeather.add(dayWeatherModel.getWeathers().get(i).getWeather());
            dwmTemp.add(String.valueOf(new DecimalFormat("#0.0").format(dayWeatherModel.getWeathers().get(i).getTemperature() - 273.15)));
        }

        args.putStringArrayList(DATE_KEY, dwmDate);
        args.putStringArrayList(WEATHER_KEY, dwmWeather);
        args.putStringArrayList(TEMP_KEY, dwmTemp);

        args.putString(DAY_KEY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentDate = getArguments().getStringArrayList(DATE_KEY);
        fragmentWeather = getArguments().getStringArrayList(WEATHER_KEY);
        fragmentTemp = getArguments().getStringArrayList(TEMP_KEY);

        fragmentDay = getArguments().getString(DAY_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weatherfragment, container, false);

        if (fragmentDay.equals("Tuesday") || fragmentDay.equals("Thursday") || fragmentDay.equals("Saturday")) {
            ListView listView = (ListView) v.findViewById(R.id.fragmentListView);
            listView.setAdapter(new LvAdapter(getContext(), fragmentDate, fragmentWeather, fragmentTemp));
        } else {
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragmentRecyclerView);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);

            RvAdapter rvAdapter = new RvAdapter(fragmentDate, fragmentWeather, fragmentTemp);
            recyclerView.setAdapter(rvAdapter);
        }

        return v;
    }
}
