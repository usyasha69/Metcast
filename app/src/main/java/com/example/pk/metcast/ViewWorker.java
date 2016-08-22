package com.example.pk.metcast;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pk.metcast.custom_views.CustomTextView;


public class ViewWorker {

    Context context;
    String date;
    String weather;
    String temperature;

    View view;

    public ViewWorker(Context context, String date, String weather, String temperature) {
        this.context = context;
        this.date = date;
        this.weather = weather;
        this.temperature = temperature;
    }


    public View makeView(LayoutInflater layoutInflater
            , ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.view_custom, viewGroup, false);


        //time text view
        ((CustomTextView) view.findViewById(R.id.time)).setText(getTime(getFullTime(date)));

        //date text view
        ((CustomTextView) view.findViewById(R.id.date)).setText(getFormatDate(date));

        //weather text view
        ((CustomTextView) view.findViewById(R.id.weather)).setText(weather);

        //temperature text view
        ((CustomTextView) view.findViewById(R.id.temperature)).setText(String.format("%s°C", temperature));

        //set weather images
        setWeatherImage();

        //set time images
        setTimeImage();

        return view;
    }

    /**
     * This method calculate time
     * in hour and set the weather
     * images.
     */
    private void setWeatherImage() {
        //get time in hours
        int hours = getHour(getFullTime(date));

        ImageView weatherImage = (ImageView) view.findViewById(R.id.weather_image);

        //condition for set weather image
        boolean timeOfDayCondition = hours > 3 && hours < 21;

        //set weather image
        switch (weather) {
            case "few clouds":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_few_broken_scattered_clouds));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_few_broken_scarreted_clouds));
                }
                break;
            case "broken clouds":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_few_broken_scattered_clouds));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_few_broken_scarreted_clouds));
                }
                break;
            case "light snow":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_snow_light_snow));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_snow_light_snow));
                }
                break;
            case "snow":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_snow_light_snow));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_snow_light_snow));
                }
                break;
            case "sky is clear":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_clear_sky));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_clear_sky));
                }
                break;
            case "light rain":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_light_rain));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_light_rain));
                }
                break;
            case "scattered clouds":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_few_broken_scattered_clouds));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_few_broken_scarreted_clouds));
                }
                break;
            case "overcast clouds":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_overcast_clouds));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_overcast_clouds));
                }
                break;
            case "moderate rain":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_moderate_rain));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_moderate_rain));
                }
                break;
            case "heavy intensity rain":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_heavy_intensity_rain));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_heavy_intensity_rain));
                }
                break;
            case "clear sky":
                if (timeOfDayCondition) {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.dt_clear_sky));
                } else {
                    weatherImage.setImageDrawable(ContextCompat.getDrawable(context
                            , R.drawable.nt_clear_sky));
                }
        }
    }

    /**
     * This method calculate
     * time and set the time
     * images.
     */
    private void setTimeImage() {
        ImageView timeImage = (ImageView) view.findViewById(R.id.time_image);

        //set time images
        switch (getFullTime(date)) {
            case "00:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.time_twelve));
                break;
            case "03:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.time_three));
                break;
            case "06:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.time_six));
                break;
            case "09:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.time_nine));
                break;
            case "12:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.time_twelve));
                break;
            case "15:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.time_three));
                break;
            case "18:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.time_six));
                break;
            case "21:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.time_nine));
                break;
        }
    }

    /**
     * This method format the
     * date
     *
     * @param date - text date
     * @return format date
     */
    private String getFormatDate(String date) {
        String result = "";

        String[] firstSplit = date.split(" "); //2016-08-17 and 21:00:00

        String[] secondSplit = firstSplit[0].split("-"); //2016 and 08 and 17

        switch (secondSplit[1]) {
            case "01":
                result += "January";
                break;
            case "02":
                result += "February";
                break;
            case "03":
                result += "March";
                break;
            case "04":
                result += "April";
                break;
            case "05":
                result += "May";
                break;
            case "06":
                result += "June";
                break;
            case "07":
                result += "July";
                break;
            case "08":
                result += "August";
                break;
            case "09":
                result += "September";
                break;
            case "10":
                result += "October";
                break;
            case "11":
                result += "November";
                break;
            case "12":
                result += "December";
                break;
        }

        //number of day in month
        String day = secondSplit[2];

        //result date
        result += ", " + day;

        return result;
    }

    /**
     * This method calculate and
     * return full time
     *
     * @param date full date
     * @return full time
     */
    private String getFullTime(String date) {
        String[] time = date.split(" "); //2016-08-18 and 18:00:00

        return time[time.length - 1]; //18:00:00
    }

    /**
     * This method calculate and
     * return hour
     *
     * @param time - full time
     * @return hour
     */
    private int getHour(String time) {
        int result;

        String[] array = time.split(":"); //18 and 00 and 00

        result = Integer.parseInt(array[0]);

        return result;
    }

    /**
     * This method calculate and
     * return time
     *
     * @param fullTime - full time
     * @return time
     */
    private String getTime(String fullTime) {
        String result;

        String[] array = fullTime.split(":"); //18 and 00 and 00

        result = array[0] + ":" + array[1];

        return result;
    }
}
