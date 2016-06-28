package com.example.pk.metcast;

import com.google.gson.Gson;

public class WeatherParsing {

    public WeatherModel parseQuery(String query) {

        final Gson gson = new Gson();
        WeatherModel weatherModel = gson.fromJson(query, WeatherModel.class);
        return weatherModel;
    }
}
