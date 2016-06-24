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
                weatherModel.setListDt(dtJSonObject.getInt("listDt"));

                //mainObjects
                JSONObject mainJSonObject = listJsonArray.getJSONObject(1);

                weatherModel.setListMainTemp(mainJSonObject.getDouble("temp"));
                weatherModel.setListMainTemp_min(mainJSonObject.getDouble("temp_min"));
                weatherModel.setListMainTemp_max(mainJSonObject.getDouble("temp_max"));
                weatherModel.setListMainPressure(mainJSonObject.getDouble("pressure"));
                weatherModel.setListMainSea_level(mainJSonObject.getDouble("sea_level"));
                weatherModel.setListMainGrnd_level(mainJSonObject.getDouble("grnd_level"));
                weatherModel.setListMainHumidity(mainJSonObject.getInt("humidity"));
                weatherModel.setListMainTemp_kf(mainJSonObject.getInt("temp_kf"));

                //Weather object
                JSONArray weatherJsonArray = listJsonArray.getJSONArray(2);

                for (int j = 0; j < weatherJsonArray.length(); j++) {
                    weatherModel.setListWeatherId(weatherJsonArray.getString(0));
                    weatherModel.setListWeatherMain(weatherJsonArray.getString(1));
                    weatherModel.setListWeatherDescription(weatherJsonArray.getString(2));
                    weatherModel.setListWeatherIcon(weatherJsonArray.getString(3));
                }


                //Clouds object
                JSONObject cloudsJSonObject = listJsonArray.getJSONObject(3);

                weatherModel.setListCloudsAll(cloudsJSonObject.getInt("all"));

                //Wind object
                JSONObject windJsonObject = listJsonArray.getJSONObject(4);

                weatherModel.setListWindSpeed(windJsonObject.getDouble("speed"));
                weatherModel.setListWindDeg(windJsonObject.getDouble("deg"));

                //Sys object
                JSONObject podJsonObject = listJsonArray.getJSONObject(5);

                weatherModel.setListSysPod(podJsonObject.getString("pod"));

                //Date_text object
                JSONObject dt_txtJsonObject = listJsonArray.getJSONObject(6);

                weatherModel.setListDt_txt(dt_txtJsonObject.getString("dt_txt"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return weatherModel;
    }
}
