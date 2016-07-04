package com.example.pk.metcast;

import com.example.pk.metcast.models.WeatherParsingModel;
import com.google.gson.Gson;

public class WeatherParsing {

    public WeatherParsingModel parseQuery(String query) {

        final Gson gson = new Gson();
        return gson.fromJson(query, WeatherParsingModel.class);
    }
}
