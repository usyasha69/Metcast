package com.example.pk.metcast;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ImageWorker {

    public View makeView(int i, LayoutInflater layoutInflater, ViewGroup viewGroup
            , ArrayList<String> dates, ArrayList<String> weather, ArrayList<String> temps
            , Context context) {

        View view = layoutInflater.inflate(R.layout.view_custom, viewGroup, false);

        ((TextView) view.findViewById(R.id.rvDateText)).setText(dates.get(i));
        ((TextView) view.findViewById(R.id.rvWeatherText)).setText(weather.get(i));
        ((TextView) view.findViewById(R.id.rvTempText)).setText(temps.get(i));

        ImageView timeImage = (ImageView) view.findViewById(R.id.time_image);
        ImageView weatherImage = (ImageView) view.findViewById(R.id.weather_image);

        switch (weather.get(i)) {
            case "few clouds":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_few_broken_scattered_clouds));
                break;
            case "broken clouds":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_few_broken_scattered_clouds));
                break;
            case "light snow":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_snow_light_snow));
                break;
            case "snow":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_snow_light_snow));
                break;
            case "clear sky":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_clear_sky));
                break;
            case "light rain":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_light_rain));
                break;
            case "scattered clouds":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_few_broken_scattered_clouds));
                break;
            case "overcast clouds":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_overcast_clouds));
                break;
            case "moderate rain":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_moderate_rain));
                break;
            case "heavy intensity rain":
                weatherImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dt_heavy_intensity_rain));
                break;
        }

        switch (getTime(dates.get(i))) {
            case "00:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.time_twelve));
                break;
            case "03:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.time_three));
                break;
            case "06:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.time_six));
                break;
            case "09:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.time_nine));
                break;
            case "12:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.time_twelve));
                break;
            case "15:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.time_three));
                break;
            case "18:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.time_six));
                break;
            case "21:00:00":
                timeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.time_nine));
                break;

        }

        return view;
    }

    private String getTime(String date) {
        String[] time = date.split(" ");

        return time[time.length - 1];
    }
}
