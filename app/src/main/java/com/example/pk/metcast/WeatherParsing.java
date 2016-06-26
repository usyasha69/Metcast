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
            weatherModel.setCountry(cityJsonObject.getString("country"));

            //Cod object
            weatherModel.setCod(dataJsonObject.getString("cod"));

            //Message object
            weatherModel.setMessage(dataJsonObject.getDouble("message"));

            //Cnt object
            weatherModel.setCnt(dataJsonObject.getInt("cnt"));

            //List of objects
            JSONArray listJsonArray = dataJsonObject.getJSONArray("list");

            for (int i = 0; i < listJsonArray.length(); i++) {
                //dt Object
                weatherModel.setListDt(listJsonArray.getJSONObject(i).getInt("dt"));

                //mainObjects
                JSONObject mainJSonObject = listJsonArray.getJSONObject(i).getJSONObject("main");

                weatherModel.setListMainTemp(mainJSonObject.getDouble("temp"));
                weatherModel.setListMainTemp_min(mainJSonObject.getDouble("temp_min"));
                weatherModel.setListMainTemp_max(mainJSonObject.getDouble("temp_max"));
                weatherModel.setListMainPressure(mainJSonObject.getDouble("pressure"));
                weatherModel.setListMainSea_level(mainJSonObject.getDouble("sea_level"));
                weatherModel.setListMainGrnd_level(mainJSonObject.getDouble("grnd_level"));
                weatherModel.setListMainHumidity(mainJSonObject.getInt("humidity"));
                weatherModel.setListMainTemp_kf(mainJSonObject.getInt("temp_kf"));

                //Weather object
                JSONArray weatherJsonArray = listJsonArray.getJSONObject(i).getJSONArray("weather");

                for (int j = 0; j < weatherJsonArray.length(); j++) {

                    weatherModel.setListWeatherId(weatherJsonArray.getJSONObject(j).getInt("id"));
                    weatherModel.setListWeatherMain(weatherJsonArray.getJSONObject(j).getString("main"));
                    weatherModel.setListWeatherDescription(weatherJsonArray.getJSONObject(j).getString("description"));
                    weatherModel.setListWeatherIcon(weatherJsonArray.getJSONObject(j).getString("icon"));
                }


                //Clouds object
                JSONObject cloudsJSonObject = listJsonArray.getJSONObject(i).getJSONObject("clouds");

                weatherModel.setListCloudsAll(cloudsJSonObject.getInt("all"));

                //Wind object
                JSONObject windJsonObject = listJsonArray.getJSONObject(i).getJSONObject("wind");

                weatherModel.setListWindSpeed(windJsonObject.getDouble("speed"));
                weatherModel.setListWindDeg(windJsonObject.getDouble("deg"));

                //Sys object
                JSONObject podJsonObject = listJsonArray.getJSONObject(i).getJSONObject("sys");

                weatherModel.setListSysPod(podJsonObject.getString("pod"));

                //Date_text object
                weatherModel.setListDt_txt(listJsonArray.getJSONObject(i).getString("dt_txt"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return weatherModel;
    }
}
