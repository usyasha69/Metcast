package com.example.pk.metcast;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetQueryTask extends AsyncTask<Void, String, String> {

    private Location location;
    private RequestResultCallback requestResultCallback;

    public GetQueryTask(Location location, RequestResultCallback requestResultCallback) {

        this.location = location;
        this.requestResultCallback = requestResultCallback;
    }

    HttpURLConnection urlConnection = null;

    BufferedReader reader = null;

    @Override
    protected String doInBackground(Void... voids) {

        String resultJSon = "";

        String query = "http://api.openweathermap.org/data/2.5/forecast?";
        query += ("lat=" + String.valueOf(location.getLatitude())
                + "&"
                + "lan="
                + String.valueOf(location.getLongitude())
                + "&APPID=4c898f591f4e595efcdd5db855f26762");

        try {
            URL url = new URL(query);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJSon = buffer.toString();

        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultJSon;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        requestResultCallback.onRequestStart();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        requestResultCallback.onRequestFinish(s);
    }

    public interface RequestResultCallback {

        void onRequestStart();
        void onRequestFinish(String result);
    }
}
