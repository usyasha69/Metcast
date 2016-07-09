package com.example.pk.metcast;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pk.metcast.models.DayWeatherModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WorkWithDB {

    public void storingDataOnTheDB(Context context, ArrayList<DayWeatherModel> list) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getWeathers().size(); j++) {
                contentValues.put(DBHelper.KEY_DAY_OF_WEEK, list.get(i).getDay());
                contentValues.put(DBHelper.KEY_DATE, list.get(i).getWeathers().get(j).getTime());
                contentValues.put(DBHelper.KEY_WEATHER, list.get(i).getWeathers().get(j).getWeather());
                contentValues.put(DBHelper.KEY_TEMP,
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
            int tempColInd = cursor.getColumnIndex(DBHelper.KEY_TEMP);

            do {
                System.out.println("ID: " + cursor.getString(idColInd)
                        + " DAY_OF_WEEK: " + cursor.getString(dayOfWeekColInd)
                        + " DATE: " + cursor.getString(dateColInd)
                        + " WEATHER: " + cursor.getString(weatherColInd)
                        + " TEMP: " + cursor.getString(tempColInd));
            } while (cursor.moveToNext());
        } else {
            System.out.println("0 rows");
        }
        cursor.close();
        dbHelper.close();
    }
}
