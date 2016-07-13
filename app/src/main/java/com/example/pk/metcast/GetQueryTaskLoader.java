package com.example.pk.metcast;


import android.content.Context;
import android.location.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetQueryTaskLoader extends android.support.v4.content.AsyncTaskLoader<String> {

    private Location location;

    public GetQueryTaskLoader(Context context, Location location) {
        super(context);
        this.location = location;
    }

    @Override
    public String loadInBackground() {
        HttpURLConnection urlConnection;
        BufferedReader reader;

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
}
