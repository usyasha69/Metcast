package com.example.pk.metcast.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pk.metcast.R;

import java.util.ArrayList;

import butterknife.BindView;


public class NavigationDrawerListViewAdapter extends BaseAdapter {

    private ArrayList<String> days;

    private Context context;

    private LayoutInflater layoutInflater;

    public NavigationDrawerListViewAdapter(Context context, ArrayList<String> days) {
        this.days = days;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int i) {
        return days.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.view_custom_navigation_drawer, viewGroup, false);

        ((TextView) view.findViewById(R.id.day)).setText(days.get(i));

        setDaysImage(view, i);

        return view;
    }

    /**
     * This method set days images.
     *
     * @param view - view
     * @param i    - position in Array List
     */
    private void setDaysImage(View view, int i) {
        ImageView dayImage = (ImageView) view.findViewById(R.id.day_image);

        switch (days.get(i)) {
            case "Monday":
                dayImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.monday));
                break;
            case "Tuesday":
                dayImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.tuesday));
                break;
            case "Wednesday":
                dayImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.wednesday));
                break;
            case "Thursday":
                dayImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.thursday));
                break;
            case "Friday":
                dayImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.friday));
                break;
            case "Saturday":
                dayImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.saturday));
                break;
            case "Sunday":
                dayImage.setImageDrawable(ContextCompat.getDrawable(context
                        , R.drawable.sunday));
                break;
        }
    }
}
