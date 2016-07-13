package com.example.pk.metcast;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherInfoModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class WorkWithDB {

    private final String MY_TAG = "myLog";

    //storing data to the database
    public void storingDataOnTheDB(Context context, ArrayList<DayWeatherModel> list) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getWeathers().size(); j++) {
                contentValues.put(DBHelper.KEY_DAY_OF_WEEK, list.get(i).getDay());
                contentValues.put(DBHelper.KEY_DATE, list.get(i).getWeathers().get(j).getTime());
                contentValues.put(DBHelper.KEY_WEATHER, list.get(i).getWeathers().get(j).getWeather());
                contentValues.put(DBHelper.KEY_TEMPERATURE,
                        new DecimalFormat("#0.0").format(list.get(i).getWeathers().get(j).getTemperature() - 273.15));

                sqLiteDatabase.insert(DBHelper.TABLE_METCAST, null, contentValues);
            }
        }


        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_METCAST, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int idColInd = cursor.getColumnIndex(DBHelper.KEY_ID);
            int dayOfWeekColInd = cursor.getColumnIndex(DBHelper.KEY_DAY_OF_WEEK);
            int dateColInd = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int weatherColInd = cursor.getColumnIndex(DBHelper.KEY_WEATHER);
            int tempColInd = cursor.getColumnIndex(DBHelper.KEY_TEMPERATURE);

            do {
                Log.d(MY_TAG, "ID: " + cursor.getString(idColInd)
                        + " DAY_OF_WEEK: " + cursor.getString(dayOfWeekColInd)
                        + " DATE: " + cursor.getString(dateColInd)
                        + " WEATHER: " + cursor.getString(weatherColInd)
                        + " TEMP: " + cursor.getString(tempColInd));
            } while (cursor.moveToNext());
        } else {
            Log.d(MY_TAG, "0 rows");
        }
        cursor.close();
        dbHelper.close();
    }

    //read data from database
    public ArrayList<DayWeatherModel> readDataFromBD(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ArrayList<DayWeatherModel> dwimlist  = new ArrayList<>();

        LinkedHashSet<String> daysOfWeek = showDaysOfWeek(context);

        Cursor cursor = null;

        for (String s : daysOfWeek) {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_METCAST, null,
                    DBHelper.KEY_DAY_OF_WEEK + " = ?",new String[] {s}, null, null, null);
            DayWeatherModel dayWeatherModel = new DayWeatherModel();
            dayWeatherModel.setDay(s);

            ArrayList<WeatherInfoModel> wimList = new ArrayList<>();

            if (cursor.moveToFirst()) {
                int dateColInd = cursor.getColumnIndex(DBHelper.KEY_DATE);
                int weatherColInd = cursor.getColumnIndex(DBHelper.KEY_WEATHER);
                int tempColInd = cursor.getColumnIndex(DBHelper.KEY_TEMPERATURE);

                do {
                    WeatherInfoModel weatherInfoModel = new WeatherInfoModel();
                    weatherInfoModel.setTime(cursor.getString(dateColInd));
                    weatherInfoModel.setWeather(cursor.getString(weatherColInd));
                    weatherInfoModel.setTemperature(Double.parseDouble(cursor.getString(tempColInd)) + 273.15);
                    wimList.add(weatherInfoModel);
                } while (cursor.moveToNext());
            } else {
                Log.d(MY_TAG, "0 rows");
            }
            dayWeatherModel.setWeathers(wimList);
            dwimlist.add(dayWeatherModel);
    }
        cursor.close();
        dbHelper.close();

        return dwimlist;
    }

    //subsidiary method
    private LinkedHashSet<String> showDaysOfWeek(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_METCAST, null, null, null, null, null, null);

        LinkedHashSet<String> set = new LinkedHashSet<>();

        if (cursor.moveToFirst()) {
            int dayOfWeekColInd = cursor.getColumnIndex(DBHelper.KEY_DAY_OF_WEEK);

            do {
                set.add(cursor.getString(dayOfWeekColInd));
            } while (cursor.moveToNext());
        } else {
            Log.d(MY_TAG, "0 rows");
        }

        cursor.close();
        dbHelper.close();
        return set;
    }

    //check, database is empty
    public boolean dbEmpty(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_METCAST, null, null, null, null, null, null);

        int tableSize = 0;

        if (cursor.moveToFirst()) {
            do {
                tableSize++;
            } while (cursor.moveToNext());
        } else {
            Log.d(MY_TAG, "0 rows");
        }

        cursor.close();
        dbHelper.close();

        return tableSize == 0;
    }

    //Update database
    public void updateDB(Context context, ArrayList<DayWeatherModel> list) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        sqLiteDatabase.delete(DBHelper.TABLE_METCAST, null, null);

        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getWeathers().size(); j++) {
                contentValues.put(DBHelper.KEY_DAY_OF_WEEK, list.get(i).getDay());
                contentValues.put(DBHelper.KEY_DATE, list.get(i).getWeathers().get(j).getTime());
                contentValues.put(DBHelper.KEY_WEATHER, list.get(i).getWeathers().get(j).getWeather());
                contentValues.put(DBHelper.KEY_TEMPERATURE,
                        new DecimalFormat("#0.0").format(list.get(i).getWeathers().get(j).getTemperature() - 273.15));

                sqLiteDatabase.insert(DBHelper.TABLE_METCAST, null, contentValues);
            }
        }

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_METCAST, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int idColInd = cursor.getColumnIndex(DBHelper.KEY_ID);
            int dayOfWeekColInd = cursor.getColumnIndex(DBHelper.KEY_DAY_OF_WEEK);
            int dateColInd = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int weatherColInd = cursor.getColumnIndex(DBHelper.KEY_WEATHER);
            int tempColInd = cursor.getColumnIndex(DBHelper.KEY_TEMPERATURE);

            do {
                Log.d(MY_TAG, "ID: " + cursor.getString(idColInd)
                        + " DAY_OF_WEEK: " + cursor.getString(dayOfWeekColInd)
                        + " DATE: " + cursor.getString(dateColInd)
                        + " WEATHER: " + cursor.getString(weatherColInd)
                        + " TEMP: " + cursor.getString(tempColInd));
            } while (cursor.moveToNext());
        } else {
            Log.d(MY_TAG, "0 rows");
        }
        cursor.close();
        dbHelper.close();
    }
}
