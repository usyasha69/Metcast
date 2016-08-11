package com.example.pk.metcast;

import com.example.pk.metcast.models.WeatherParsingModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {
    @GET("forecast")
    Call<WeatherParsingModel> loadQuestions(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String appid);
}
