package com.example.pk.metcast;

import com.example.pk.metcast.models.WeatherParsingModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {
    @GET("forecast?lat=65.9667&lon=-18.5333&APPID=4c898f591f4e595efcdd5db855f26762")
    Call<WeatherParsingModel> loadQuestions(@Query("tagged") String tags);
}
