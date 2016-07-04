package com.example.pk.metcast;

import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherInfoModel;
import com.example.pk.metcast.models.WeatherParsingModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ConversionToWeather {

    public ArrayList<DayWeatherModel> group(WeatherParsingModel weatherParsingModel) {

        //WeatherInfoModel fields
        String timeString;
        String weather;
        double temperature;

        //support variables
        long timeLong;
        int dayInt;
        String dayStr;

        //getting current day
        int currentDayInt = currentDay();
        String currentDayStr = dayOfWeekToString(currentDayInt);

        //filling the data from the WeatherParsingModel
        ArrayList<String> listDateTxt = new ArrayList<>();
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listDateTxt.add(weatherParsingModel.getList().get(i).getListDtTxt());
        }

        ArrayList<String> listWeather = new ArrayList<>();
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeather.add(weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherDescription());
            }
        }

        ArrayList<Double> listTemp = new ArrayList<>();
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listTemp.add(weatherParsingModel.getList().get(i).getMain().getListMainTemp());
        }

        ArrayList<Long> listDt = new ArrayList<>();
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listDt.add(weatherParsingModel.getList().get(i).getListDt() * 1000);
        }

        ArrayList<DayWeatherModel> dayWeatherModelList = new ArrayList<>();
        ArrayList<WeatherInfoModel> supportWeatherInfoList = new ArrayList<>();

        //grouping days and weather
        for (int i = 0; i < listDateTxt.size(); i++) {
            WeatherInfoModel weatherInfoModel = new WeatherInfoModel();


            timeString = listDateTxt.get(i);
            weather = listWeather.get(i);
            temperature = listTemp.get(i);

            weatherInfoModel.setTime(timeString);
            weatherInfoModel.setWeather(weather);
            weatherInfoModel.setTemperature(temperature);

            timeLong = listDt.get(i);
            dayInt = dayFromUnixTime(timeLong);
            dayStr = dayOfWeekToString(dayInt);

            if (dayInt == currentDayInt) {
                supportWeatherInfoList.add(weatherInfoModel);
                if (i == listDateTxt.size() - 1) {

                    ArrayList<WeatherInfoModel> weatherInfoList = new ArrayList<>();
                    DayWeatherModel dayWeatherModel = new DayWeatherModel();

                    for (int j = 0; j < supportWeatherInfoList.size(); j++) {
                        weatherInfoList.add(supportWeatherInfoList.get(j));
                    }

                    dayWeatherModel.setWeathers(weatherInfoList);
                    dayWeatherModel.setDay(currentDayStr);

                    dayWeatherModelList.add(dayWeatherModel);
                }
            } else {
                ArrayList<WeatherInfoModel> weatherInfoList = new ArrayList<>();

                for (int j = 0; j < supportWeatherInfoList.size(); j++) {
                    weatherInfoList.add(supportWeatherInfoList.get(j));
                }

                DayWeatherModel dayWeatherModel = new DayWeatherModel();

                dayWeatherModel.setWeathers(weatherInfoList);
                dayWeatherModel.setDay(currentDayStr);

                dayWeatherModelList.add(dayWeatherModel);

                currentDayInt = dayInt;
                currentDayStr = dayStr;

                supportWeatherInfoList.clear();
                supportWeatherInfoList.add(weatherInfoModel);
            }
        }

        return dayWeatherModelList;
    }

    //conversion unix time to day of week
    public int dayFromUnixTime(long time) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //conversion day of week to String
    public String dayOfWeekToString(int day) {

        String dayString = "";

        switch (day) {
            case 1:
                dayString = "Sunday";
                break;
            case 2:
                dayString = "Monday";
                break;
            case 3:
                dayString = "Tuesday";
                break;
            case 4:
                dayString = "Wednesday";
                break;
            case 5:
                dayString = "Thursday";
                break;
            case 6:
                dayString = "Friday";
                break;
            case 7:
                dayString = "Saturday";
                break;
            default:break;
        }
        return dayString;
    }

    //getting current day
    public int currentDay() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}

