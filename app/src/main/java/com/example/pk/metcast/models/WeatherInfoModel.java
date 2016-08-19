package com.example.pk.metcast.models;

import java.io.Serializable;

public class WeatherInfoModel implements Serializable {

    private String time;
    private String weather;
    private double temperature;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
