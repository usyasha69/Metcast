package com.example.pk.metcast;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "metcastDB";
    public static final String TABLE_METCAST = "metcast";

    public static final String KEY_ID = "_id";
    public static final String KEY_DAY_OF_WEEK = "dayOfWeek";
    public static final String KEY_DATE = "date";
    public static final String KEY_WEATHER = "weather";
    public static final String KEY_TEMP = "temperature";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_METCAST + " ("
                + KEY_ID + " integer primary key," + KEY_DAY_OF_WEEK
                + " text," + KEY_DATE + " text," + KEY_WEATHER + " text,"
                + KEY_TEMP + " real" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_METCAST);

        onCreate(sqLiteDatabase);
    }
}
