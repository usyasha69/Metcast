package com.example.pk.metcast;

import android.location.Location;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParseTask extends AsyncTask<Void, Void, String> {

    Location location;

    public ParseTask(Location location) {
        this.location = location;
    }

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJSon = "";


    @Override
    protected String doInBackground(Void... voids) {

        String query = "api.openweathermap.org/data/2.5/forecast?";
        query += ("lat=" + String.valueOf(location.getLatitude()) + "&" + "lan=" + String.valueOf(location.getLongitude()));

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultJSon;
    }

    @Override
    protected void onPostExecute(String strResultJson) {
        super.onPostExecute(strResultJson);

        JSONObject dataJsonObject = null;

        String cityId;
        String cityName;
        String cityLonCoord;
        String cityLatCoord;
        String country;
        String cod;
        String message;
        String cnt;

        try {
            //All object
            dataJsonObject = new JSONObject(strResultJson);

            //City object
            JSONObject cityJsonObject = dataJsonObject.getJSONObject("city");

            //City id object
            JSONObject cityIdJsonObject = cityJsonObject.getJSONObject("id");
            cityId = cityIdJsonObject.getString("id");

            //City name object
            JSONObject cityNameJsonObject = cityJsonObject.getJSONObject("name");
            cityName = cityNameJsonObject.getString("name");

            //City coord object
            JSONObject cityCoordJsonObject = cityJsonObject.getJSONObject("coord");

            //City coord lon object
            JSONObject cityLonCoordJsonObject = cityCoordJsonObject.getJSONObject("lon");
            cityLonCoord = cityLonCoordJsonObject.getString("lon");

            //City coord lat object
            JSONObject cityLatCoordJsonObject = cityCoordJsonObject.getJSONObject("lat");
            cityLatCoord = cityLatCoordJsonObject.getString("lat");

            //Country object
            JSONObject countryJsonObject = dataJsonObject.getJSONObject("country");
            country = countryJsonObject.getString("country");

            //Cod object
            JSONObject codJsonObject = dataJsonObject.getJSONObject("cod");
            cod = codJsonObject.getString("cod");

            //Message object
            JSONObject messageJsonObject = dataJsonObject.getJSONObject("message");
            message = codJsonObject.getString("message");

            //Cnt object
            JSONObject cntJsonObject = dataJsonObject.getJSONObject("cnt");
            cnt = codJsonObject.getString("cnt");


            JSONArray jsonArray = dataJsonObject.getJSONArray("list");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
