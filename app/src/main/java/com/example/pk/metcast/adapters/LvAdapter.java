package com.example.pk.metcast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pk.metcast.ImageWorker;

import java.util.ArrayList;

public class LvAdapter extends BaseAdapter {

    ArrayList<String> dates;
    ArrayList<String> weathers;
    ArrayList<String> temps;

    Context context;

    LayoutInflater layoutInflater;

    public LvAdapter(Context context, ArrayList<String> dates, ArrayList<String> weathers, ArrayList<String> temps) {
        this.dates = dates;
        this.weathers = weathers;
        this.temps = temps;
        this.context = context;
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

        return new ImageWorker().makeView(i, layoutInflater, viewGroup
                , dates, weathers, temps, context);

    }
}
