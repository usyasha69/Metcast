package com.example.pk.metcast.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.pk.metcast.WorkWithDB;
import com.example.pk.metcast.models.DayWeatherModel;

import java.util.ArrayList;

public class InsertToDBLoader extends AsyncTaskLoader<String> {

    ArrayList<DayWeatherModel> list;

    public InsertToDBLoader(Context context, ArrayList<DayWeatherModel> list) {
        super(context);
        this.list = list;
    }

    @Override
    public String loadInBackground() {
        new WorkWithDB().insertToDB(getContext(), list);
        return null;
    }
}
