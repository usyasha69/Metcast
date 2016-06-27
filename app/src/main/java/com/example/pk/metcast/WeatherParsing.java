package com.example.pk.metcast;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherParsing {

    JSONObject dataJsonObject = null;

    WeatherModel weatherModel = new WeatherModel();

    public WeatherModel parseQuery(String strResultJson) {
        try {

            dataJsonObject = new JSONObject(strResultJson);

            JSONObject cityJsonObject = dataJsonObject.getJSONObject("city");

            weatherModel.setCityId(cityJsonObject.getInt("id"));

            weatherModel.setCityName(cityJsonObject.getString("name"));

            JSONObject cityCoordJsonObject = cityJsonObject.getJSONObject("coord");

            weatherModel.setCityLonCoord(cityCoordJsonObject.getDouble("lon"));

            weatherModel.setCityLatCoord(cityCoordJsonObject.getDouble("lat"));

            weatherModel.setCountry(cityJsonObject.getString("country"));

            weatherModel.setCod(dataJsonObject.getString("cod"));

            weatherModel.setMessage(dataJsonObject.getDouble("message"));

            weatherModel.setCnt(dataJsonObject.getInt("cnt"));

            JSONArray listJsonArray = dataJsonObject.getJSONArray("list");

            for (int i = 0; i < listJsonArray.length(); i++) {

                weatherModel.setListDt(listJsonArray.getJSONObject(i).getInt("dt"));

                JSONObject mainJSonObject = listJsonArray.getJSONObject(i).getJSONObject("main");

                weatherModel.setListMainTemp(mainJSonObject.getDouble("temp"));
                weatherModel.setListMainTemp_min(mainJSonObject.getDouble("temp_min"));
                weatherModel.setListMainTemp_max(mainJSonObject.getDouble("temp_max"));
                weatherModel.setListMainPressure(mainJSonObject.getDouble("pressure"));
                weatherModel.setListMainSea_level(mainJSonObject.getDouble("sea_level"));
                weatherModel.setListMainGrnd_level(mainJSonObject.getDouble("grnd_level"));
                weatherModel.setListMainHumidity(mainJSonObject.getInt("humidity"));
                weatherModel.setListMainTemp_kf(mainJSonObject.getInt("temp_kf"));

                JSONArray weatherJsonArray = listJsonArray.getJSONObject(i).getJSONArray("weather");

                for (int j = 0; j < weatherJsonArray.length(); j++) {

                    weatherModel.setListWeatherId(weatherJsonArray.getJSONObject(j).getInt("id"));
                    weatherModel.setListWeatherMain(weatherJsonArray.getJSONObject(j).getString("main"));
                    weatherModel.setListWeatherDescription(weatherJsonArray.getJSONObject(j).getString("description"));
                    weatherModel.setListWeatherIcon(weatherJsonArray.getJSONObject(j).getString("icon"));
                }


                JSONObject cloudsJSonObject = listJsonArray.getJSONObject(i).getJSONObject("clouds");

                weatherModel.setListCloudsAll(cloudsJSonObject.getInt("all"));

                JSONObject windJsonObject = listJsonArray.getJSONObject(i).getJSONObject("wind");

                weatherModel.setListWindSpeed(windJsonObject.getDouble("speed"));
                weatherModel.setListWindDeg(windJsonObject.getDouble("deg"));

                JSONObject podJsonObject = listJsonArray.getJSONObject(i).getJSONObject("sys");

                weatherModel.setListSysPod(podJsonObject.getString("pod"));

                weatherModel.setListDtTxt(listJsonArray.getJSONObject(i).getString("dt_txt"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return weatherModel;
    }
}
