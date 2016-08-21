package com.example.pk.metcast.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.pk.metcast.database.DBWorker;
import com.example.pk.metcast.models.DayWeatherModel;

import java.util.ArrayList;

public class InsertToDBLoader extends AsyncTaskLoader<String> {

    //array list with weather
    ArrayList<DayWeatherModel> list;

    public InsertToDBLoader(Context context, ArrayList<DayWeatherModel> list) {
        super(context);
        this.list = list;
    }

    @Override
    public String loadInBackground() {
        new DBWorker().insertToDB(getContext(), list);
        return null;
    }
}
