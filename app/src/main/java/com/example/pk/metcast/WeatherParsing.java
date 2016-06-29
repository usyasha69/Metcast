package com.example.pk.metcast;

import com.google.gson.Gson;

public class WeatherParsing {

    public WeatherParsingModel parseQuery(String query) {

        final Gson gson = new Gson();
        WeatherParsingModel weatherParsingModel = gson.fromJson(query, WeatherParsingModel.class);
        return weatherParsingModel;
    }
}
