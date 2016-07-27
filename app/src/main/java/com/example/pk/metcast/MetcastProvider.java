package com.example.pk.metcast;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class MetcastProvider extends ContentProvider {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "metcastDB";
    public static final String TABLE_METCAST = "metcast";

    public static final String KEY_ID = "_id";
    public static final String KEY_DAY_OF_WEEK = "dayOfWeek";
    public static final String KEY_DATE = "date";
    public static final String KEY_WEATHER = "weather";
    public static final String KEY_TEMPERATURE = "temperature";

    public static final String DB_CREATE = "create table " + TABLE_METCAST + " ("
            + KEY_ID + " integer primary key," + KEY_DAY_OF_WEEK
            + " text," + KEY_DATE + " text," + KEY_WEATHER + " text,"
            + KEY_TEMPERATURE + " real" + ");";

    public static final String AUTHORITY = "com.example.pk.metcast";
    public static final String METCAST_PATH = "weather";

    public static final Uri METCAST_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + METCAST_PATH);

    public static final String METCAST_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + METCAST_PATH;
    public static final String METCAST_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + METCAST_PATH;

    public static final int URI_METCAST = 1;
    public static final int URI_METCAST_ID = 2;

    ContentResolver contentResolver;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, METCAST_PATH, URI_METCAST);
        uriMatcher.addURI(AUTHORITY, METCAST_PATH + "/#", URI_METCAST_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        contentResolver = getContext().getContentResolver();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_METCAST, strings, s, strings1, null, null, s1);

        cursor.setNotificationUri(contentResolver, METCAST_CONTENT_URI);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_METCAST:
                return METCAST_CONTENT_TYPE;
            case URI_METCAST_ID:
                return METCAST_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        if (uriMatcher.match(uri) != URI_METCAST)
            throw new IllegalArgumentException("Wrong URI " + uri);

        sqLiteDatabase = dbHelper.getWritableDatabase();
        long rowID = sqLiteDatabase.insert(TABLE_METCAST, null, contentValues);
        Uri resultURI = ContentUris.withAppendedId(METCAST_CONTENT_URI, rowID);
        contentResolver.notifyChange(resultURI, null);
        return resultURI;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int cnt = sqLiteDatabase.delete(TABLE_METCAST, s, strings);
        contentResolver.notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int cnt = sqLiteDatabase.update(TABLE_METCAST, contentValues, s, strings);
        contentResolver.notifyChange(uri, null);
        return cnt;
    }

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists " + TABLE_METCAST);
            onCreate(sqLiteDatabase);
        }
    }
}
