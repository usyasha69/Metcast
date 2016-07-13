package com.example.pk.metcast;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;

import com.example.pk.metcast.models.DayWeatherModel;

import java.util.ArrayList;


public class DatabaseCursorLoader extends AsyncTaskLoader<String> {

    ArrayList<DayWeatherModel> list;
    Context context;

    public DatabaseCursorLoader(Context context, ArrayList<DayWeatherModel> list) {
        super(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public String loadInBackground() {
        WorkWithDB workWithDB = new WorkWithDB();

        if (workWithDB.dbEmpty(context)) {
            workWithDB.storingDataOnTheDB(context, list);
        } else {
            workWithDB.updateDB(context, list);
        }
        return null;
    }
}
