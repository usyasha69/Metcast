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

        int cityId;
        String cityName;
        double cityLonCoord;
        double cityLatCoord;
        String country;
        String cod;
        double message;
        int cnt;
        int listDt;
        double listMainTemp;
        double listMainTemp_min;
        double listMainTemp_max;
        double listMainPressure;
        double listMainSea_level;
        double listMainGrnd_level;
        int listMainHumidity;
        double listMainTemp_kf;
        String listWeatherId;
        String listWeatherMain;
        String listWeatherDescription;
        String listWeatherIcon;
        int listCloudsAll;
        double listWindSpeed;
        double listWindDeg;
        String listSysPod;
        String listDt_txt;

        try {
            //All object
            dataJsonObject = new JSONObject(strResultJson);

            //City object
            JSONObject cityJsonObject = dataJsonObject.getJSONObject("city");

            cityId = cityJsonObject.getInt("id");

            cityName = cityJsonObject.getString("name");

            //City coord object
            JSONObject cityCoordJsonObject = cityJsonObject.getJSONObject("coord");

            cityLonCoord = cityCoordJsonObject.getDouble("lon");
            
            cityLatCoord = cityCoordJsonObject.getDouble("lat");

            //Country object
            JSONObject countryJsonObject = dataJsonObject.getJSONObject("country");
            country = countryJsonObject.getString("country");

            //Cod object
            JSONObject codJsonObject = dataJsonObject.getJSONObject("cod");
            cod = codJsonObject.getString("cod");

            //Message object
            JSONObject messageJsonObject = dataJsonObject.getJSONObject("message");
            message = messageJsonObject.getDouble("message");

            //Cnt object
            JSONObject cntJsonObject = dataJsonObject.getJSONObject("cnt");
            cnt = codJsonObject.getInt("cnt");

            //List of objects
            JSONArray listJsonArray = dataJsonObject.getJSONArray("list");

            for (int i = 0; i < listJsonArray.length(); i++) {
                //dt Object
                JSONObject dtJSonObject = listJsonArray.getJSONObject(0);
                listDt = dtJSonObject.getInt("listDt");

                //mainObjects
                JSONObject mainJSonObject = listJsonArray.getJSONObject(1);

                listMainTemp = mainJSonObject.getDouble("temp");
                listMainTemp_min = mainJSonObject.getDouble("temp_min");
                listMainTemp_max = mainJSonObject.getDouble("temp_max");
                listMainPressure = mainJSonObject.getDouble("pressure");
                listMainSea_level = mainJSonObject.getDouble("sea_level");
                listMainGrnd_level = mainJSonObject.getDouble("grnd_level");
                listMainHumidity = mainJSonObject.getInt("humidity");
                listMainTemp_kf = mainJSonObject.getInt("temp_kf");

                //Weather object
                JSONArray weatherJsonArray = listJsonArray.getJSONArray(2);

                for (int j = 0; j < weatherJsonArray.length(); j++) {
                    listWeatherId = weatherJsonArray.getString(0);
                    listWeatherMain = weatherJsonArray.getString(1);
                    listWeatherDescription = weatherJsonArray.getString(2);
                    listWeatherIcon = weatherJsonArray.getString(3);
                }


                //Clouds object
                JSONObject cloudsJSonObject = listJsonArray.getJSONObject(3);

                listCloudsAll = cloudsJSonObject.getInt("all");

                //Wind object
                JSONObject windJsonObject = listJsonArray.getJSONObject(4);

                listWindSpeed = windJsonObject.getDouble("speed");
                listWindDeg = windJsonObject.getDouble("deg");

                //Sys object
                JSONObject podJsonObject = listJsonArray.getJSONObject(5);

                listSysPod = podJsonObject.getString("pod");

                //Date_text object
                JSONObject dt_txtJsonObject = listJsonArray.getJSONObject(6);

                listDt_txt = dt_txtJsonObject.getString("dt_txt");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
