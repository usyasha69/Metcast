package com.example.pk.metcast;

import com.example.pk.metcast.models.DayWeatherModel;
import com.example.pk.metcast.models.WeatherInfoModel;
import com.example.pk.metcast.models.WeatherParsingModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ConverterToWeather {

    /**
     * This method takes a weather
     * parsing model and return
     * array list with day weather models.
     *
     * @param weatherParsingModel - weather parsing model
     * @return array list with day weather model
     */
    public ArrayList<DayWeatherModel> group(WeatherParsingModel weatherParsingModel) {

        //fields for weather info model
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

        //array lists with weather
        ArrayList<String> listDateTxt = new ArrayList<>();
        ArrayList<String> listWeather = new ArrayList<>();
        ArrayList<Double> listTemp = new ArrayList<>();
        ArrayList<Long> listDt = new ArrayList<>();

        try {
            //filling the txt date from the weather parsing model
            for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
                listDateTxt.add(weatherParsingModel.getList().get(i).getListDtTxt());
            }

            //filling the weather from the weather parsing model

            for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
                for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                    listWeather.add(weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherDescription());
                }
            }

            //filling the temperature from the weather parsing model

            for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
                listTemp.add(weatherParsingModel.getList().get(i).getMain().getListMainTemp());
            }

            //filling the unix date from the weather parsing model

            for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
                listDt.add(weatherParsingModel.getList().get(i).getListDt() * 1000);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //result array list with day weather models
        ArrayList<DayWeatherModel> dayWeatherModelList = new ArrayList<>();

        //support array list with weather info models
        ArrayList<WeatherInfoModel> supportWeatherInfoList = new ArrayList<>();

        //grouping days and weather
        for (int i = 0; i < listDateTxt.size(); i++) {
            WeatherInfoModel weatherInfoModel = new WeatherInfoModel();

            //variables for weather info model
            timeString = listDateTxt.get(i);
            weather = listWeather.get(i);
            temperature = listTemp.get(i);

            //get weather
            weatherInfoModel.setTime(timeString);
            weatherInfoModel.setWeather(weather);
            weatherInfoModel.setTemperature(temperature);

            //get day in integer and string format
            timeLong = listDt.get(i);
            dayInt = dayFromUnixTime(timeLong);
            dayStr = dayOfWeekToString(dayInt);

            if (dayInt == currentDayInt) {
                supportWeatherInfoList.add(weatherInfoModel);
                if (i == listDateTxt.size() - 1) {

                    ArrayList<WeatherInfoModel> weatherInfoList = new ArrayList<>();
                    DayWeatherModel dayWeatherModel = new DayWeatherModel();

                    //filling weather info model list
                    for (int j = 0; j < supportWeatherInfoList.size(); j++) {
                        weatherInfoList.add(supportWeatherInfoList.get(j));
                    }

                    //setting weather and day in result array list
                    dayWeatherModel.setWeathers(weatherInfoList);
                    dayWeatherModel.setDay(currentDayStr);

                    dayWeatherModelList.add(dayWeatherModel);
                }
            } else {
                supportWeatherInfoList.add(weatherInfoModel);

                ArrayList<WeatherInfoModel> weatherInfoList = new ArrayList<>();

                //filling support array list with weather info model
                for (int j = 0; j < supportWeatherInfoList.size(); j++) {
                    weatherInfoList.add(supportWeatherInfoList.get(j));
                }

                DayWeatherModel dayWeatherModel = new DayWeatherModel();

                //setting weather and day in result array list
                dayWeatherModel.setWeathers(weatherInfoList);
                dayWeatherModel.setDay(currentDayStr);

                dayWeatherModelList.add(dayWeatherModel);

                //replace current day
                currentDayInt = dayInt;
                currentDayStr = dayStr;

                //clear support array list with weather info model
                supportWeatherInfoList.clear();
            }
        }

        return dayWeatherModelList;
    }

    /**
     * This method conversion unix time
     * to day of week.
     *
     * @param time - date in unix format
     * @return day of week in integer
     */
    public int dayFromUnixTime(long time) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * This method conversion day
     * of week in integer to day
     * of week in String.
     *
     * @param day - day in integer format
     * @return day of week in String
     */
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
            default:
                break;
        }
        return dayString;
    }

    /**
     * This method getting
     * current day.
     *
     * @return number of current day
     */
    public int currentDay() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}

