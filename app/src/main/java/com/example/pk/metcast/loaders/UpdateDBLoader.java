package com.example.pk.metcast.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.pk.metcast.database.DBWorker;
import com.example.pk.metcast.models.DayWeatherModel;

import java.util.ArrayList;

public class UpdateDBLoader extends AsyncTaskLoader<Integer> {

    //Array List with weather
    ArrayList<DayWeatherModel> list;

    public UpdateDBLoader(Context context, ArrayList<DayWeatherModel> list) {
        super(context);
        this.list = list;
    }

    @Override
    public Integer loadInBackground() {
        return new DBWorker().updateDB(getContext(), list);
    }
}
