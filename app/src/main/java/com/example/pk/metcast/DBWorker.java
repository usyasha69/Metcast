package com.example.pk.metcast;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherInfoModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class DBWorker {

    //Log tag
    private final String MY_TAG = "myLog";
    //weather URI
    public static final Uri METCAST_URI = Uri.parse("content://com.example.pk.metcast/weather");

    /**
     * This method insert data
     * to database
     *
     * @param context - context
     * @param list    - array list with
     *                day weather models
     */
    public void insertToDB(Context context, ArrayList<DayWeatherModel> list) {

        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getWeathers().size(); j++) {
                contentValues.put(MetCastProvider.KEY_DAY_OF_WEEK, list.get(i).getDay());
                contentValues.put(MetCastProvider.KEY_DATE, list.get(i).getWeathers().get(j).getTime());
                contentValues.put(MetCastProvider.KEY_WEATHER, list.get(i).getWeathers().get(j).getWeather());
                contentValues.put(MetCastProvider.KEY_TEMPERATURE, list.get(i).getWeathers().get(j).getTemperature());

                context.getContentResolver().insert(METCAST_URI, contentValues);
            }
        }

        Cursor cursor = context.getContentResolver().query(METCAST_URI, null, null, null, null);

        assert cursor != null;
        if (cursor.moveToFirst()) {
            do {
                Log.d(MY_TAG, "ID: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_ID))
                        + " DAY_OF_WEEK: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_DAY_OF_WEEK))
                        + " DATE: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_DATE))
                        + " WEATHER: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_WEATHER))
                        + " TEMP: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_TEMPERATURE)));
            } while (cursor.moveToNext());
        } else {
            Log.d(MY_TAG, "0 rows");
        }
        cursor.close();
    }

    /**
     * This method reading data from database
     *
     * @param cursor - cursor with data
     * @return array list with day weather models
     */
    public ArrayList<DayWeatherModel> readDataFromBD(Cursor cursor) {
        ArrayList<String> daysOfWeek = new ArrayList<>();
        ArrayList<WeatherInfoModel> wimList = new ArrayList<>();

        //read data from cursor
        if (cursor.moveToFirst()) {
            do {
                String dayOfWeek = cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_DAY_OF_WEEK));

                WeatherInfoModel wim = new WeatherInfoModel();
                wim.setTime(cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_DATE)));
                wim.setWeather(cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_WEATHER)));
                wim.setTemperature((cursor.getDouble(cursor.getColumnIndex(
                        MetCastProvider.KEY_TEMPERATURE))));

                daysOfWeek.add(dayOfWeek);
                wimList.add(wim);
            } while (cursor.moveToNext());
        } else {
            Log.d(MY_TAG, "0 rows");
        }

        return finalResult(daysOfWeek, wimList);
    }

    /**
     * This method conversion data from
     * array lists with day of week and
     * weather to array list with
     * day weather models
     *
     * @param daysOfWeek - array list with days of week
     * @param wimList    - array list with weather
     * @return array list with day weather models
     */
    private ArrayList<DayWeatherModel> finalResult(ArrayList<String> daysOfWeek, ArrayList<WeatherInfoModel> wimList) {
        ArrayList<DayWeatherModel> finalList = new ArrayList<>();

        LinkedHashSet<String> set = new LinkedHashSet<>(daysOfWeek);

        for (String s : set) {
            DayWeatherModel dayWeatherModel = new DayWeatherModel();
            dayWeatherModel.setDay(s);
            ArrayList<WeatherInfoModel> finalWimList = new ArrayList<>();
            for (int i = 0; i < daysOfWeek.size(); i++) {
                if (s.equals(daysOfWeek.get(i))) {
                    finalWimList.add(wimList.get(i));
                }
            }
            dayWeatherModel.setWeathers(finalWimList);
            finalList.add(dayWeatherModel);
        }

        return finalList;
    }

    /**
     * This method update database
     *
     * @param context - context
     * @param list    - array list with day weather models
     * @return number of updates
     */
    public int updateDB(Context context, ArrayList<DayWeatherModel> list) {
        ContentValues contentValues = new ContentValues();
        int updateCount = 0;

        if (context.getContentResolver().query(METCAST_URI, null, null, null, null, null).moveToFirst()) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(i).getWeathers().size(); j++) {
                    contentValues.put(MetCastProvider.KEY_DAY_OF_WEEK, list.get(i).getDay());
                    contentValues.put(MetCastProvider.KEY_DATE, list.get(i).getWeathers().get(j).getTime());
                    contentValues.put(MetCastProvider.KEY_WEATHER, list.get(i).getWeathers().get(j).getWeather());
                    contentValues.put(MetCastProvider.KEY_TEMPERATURE, list.get(i).getWeathers().get(j).getTemperature());
                    updateCount++;
                    context.getContentResolver().update(METCAST_URI, contentValues, "_id = ?", new String[]{String.valueOf(updateCount)});
                }
            }
        }

        Cursor cursor = context.getContentResolver().query(METCAST_URI, null, null, null, null, null);

        assert cursor != null;
        if (cursor.moveToFirst()) {
            do {
                Log.d(MY_TAG, "ID: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_ID))
                        + " DAY_OF_WEEK: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_DAY_OF_WEEK))
                        + " DATE: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_DATE))
                        + " WEATHER: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_WEATHER))
                        + " TEMP: " + cursor.getString(cursor.getColumnIndex(MetCastProvider.KEY_TEMPERATURE)));
            } while (cursor.moveToNext());
        } else {
            Log.d(MY_TAG, "0 rows");
        }
        cursor.close();
        return updateCount;
    }

    /**
     * This method checked database
     * is empty
     *
     * @param context - context
     * @return return true (database empty)
     * or false (database doesn't empty)
     */
    public boolean emptyCheckedDB(Context context) {
        Cursor cursor = context.getContentResolver().query(METCAST_URI, null, null, null, null, null);

        assert cursor != null;
        boolean b = cursor.moveToFirst();

        cursor.close();
        return b;
    }
}
