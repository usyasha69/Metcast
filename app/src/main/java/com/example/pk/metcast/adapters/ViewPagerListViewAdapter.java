package com.example.pk.metcast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pk.metcast.ViewWorker;
import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherInfoModel;

import java.util.ArrayList;

public class ViewPagerListViewAdapter extends BaseAdapter {

    private ArrayList<String> dates;
    private ArrayList<String> weathers;
    private ArrayList<String> temps;

    private Context context;

    private LayoutInflater layoutInflater;

    public ViewPagerListViewAdapter(Context context, ArrayList<String> dates, ArrayList<String> weathers, ArrayList<String> temps) {
        this.dates = dates;
        this.weathers = weathers;
        this.temps = temps;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int i) {
        WeatherInfoModel weatherInfoModel = new WeatherInfoModel();

        weatherInfoModel.setTime(dates.get(i));
        weatherInfoModel.setWeather(weathers.get(i));
        weatherInfoModel.setTemperature(Double.parseDouble(temps.get(i)));


        return weatherInfoModel;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return new ViewWorker(context, dates.get(i), weathers.get(i), temps.get(i))
                .makeView(layoutInflater, viewGroup);
    }
}
