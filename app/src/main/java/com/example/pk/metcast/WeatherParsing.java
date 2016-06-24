package com.example.pk.metcast;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherParsing {

    JSONObject dataJsonObject = null;

    WeatherModel weatherModel = new WeatherModel();

    public WeatherModel parseQuery(String strResultJson) {
        try {
            //All object
            dataJsonObject = new JSONObject(strResultJson);

            //City object
            JSONObject cityJsonObject = dataJsonObject.getJSONObject("city");

            weatherModel.setCityId(cityJsonObject.getInt("id"));

            weatherModel.setCityName(cityJsonObject.getString("name"));

            //City coord object
            JSONObject cityCoordJsonObject = cityJsonObject.getJSONObject("coord");

            weatherModel.setCityLonCoord(cityCoordJsonObject.getDouble("lon"));

            weatherModel.setCityLatCoord(cityCoordJsonObject.getDouble("lat"));

            //Country object
            JSONObject countryJsonObject = dataJsonObject.getJSONObject("country");
            weatherModel.setCountry(countryJsonObject.getString("country"));

            //Cod object
            JSONObject codJsonObject = dataJsonObject.getJSONObject("cod");
            weatherModel.setCod(codJsonObject.getString("cod"));

            //Message object
            JSONObject messageJsonObject = dataJsonObject.getJSONObject("message");
            weatherModel.setMessage(messageJsonObject.getDouble("message"));

            //Cnt object
            JSONObject cntJsonObject = dataJsonObject.getJSONObject("cnt");
            weatherModel.setCnt(codJsonObject.getInt("cnt"));

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
        return weatherModel;
    }
}
