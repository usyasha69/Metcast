package com.example.pk.metcast.models;

import java.util.ArrayList;

public class DayWeatherModel {

    private String day;
    private ArrayList<WeatherInfoModel> weathers;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<WeatherInfoModel> getWeathers() {
        return weathers;
    }

    public void setWeathers(ArrayList<WeatherInfoModel> weathers) {
        this.weathers = weathers;
    }
}
