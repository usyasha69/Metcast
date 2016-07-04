package com.example.pk.metcast;

import android.location.Location;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetQueryTask extends AsyncTask<Void, String, String> {

    private Location location;
    private RequestResultCallback requestResultCallback;
    public MainActivity mainActivity;

    public GetQueryTask(Location location, RequestResultCallback requestResultCallback) {

        this.location = location;
        this.requestResultCallback = requestResultCallback;
    }

    public HttpURLConnection urlConnection = null;

    public BufferedReader reader = null;

    void link(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    void unLink() {
        mainActivity = null;
    }

    @Override
    protected String doInBackground(Void... voids) {

        String resultJSon = "";

        String query = "http://api.openweathermap.org/data/2.5/forecast?";
        query += ("lat=" + String.valueOf(location.getLatitude())
                + "&"
                + "lon="
                + String.valueOf(location.getLongitude())
                + "&APPID=4c898f591f4e595efcdd5db855f26762");
        try {
            URL url = new URL(query);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            resultJSon = builder.toString();

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
