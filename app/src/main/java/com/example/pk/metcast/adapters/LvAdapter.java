package com.example.pk.metcast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pk.metcast.R;

import java.util.ArrayList;

public class LvAdapter extends BaseAdapter {

    ArrayList<String> dates;
    ArrayList<String> weathers;
    ArrayList<String> temps;

    LayoutInflater layoutInflater;

    public LvAdapter(Context context, ArrayList<String> dates, ArrayList<String> weathers, ArrayList<String> temps) {
        this.dates = dates;
        this.weathers = weathers;
        this.temps = temps;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int i) {
        ArrayList<String> list = new ArrayList<>();
        list.add(dates.get(i));
        list.add(weathers.get(i));
        list.add(temps.get(i));
        return list;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.view_custom, viewGroup, false);

        ((TextView) view.findViewById(R.id.rvDateText)).setText(dates.get(i));
        ((TextView) view.findViewById(R.id.rvWeatherText)).setText(weathers.get(i));
        ((TextView) view.findViewById(R.id.rvTempText)).setText(temps.get(i));
        return view;
    }
}
