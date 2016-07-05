package com.example.pk.metcast.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.metcast.R;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    ArrayList<String> dates;
    ArrayList<String> weathers;
    ArrayList<String> temps;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTv;
        public TextView weatherTv;
        public TextView tempTv;
        public ViewHolder(View v) {
            super(v);
            dateTv = (TextView) v.findViewById(R.id.rvDateText);
            weatherTv = (TextView) v.findViewById(R.id.rvWeatherText);
            tempTv = (TextView) v.findViewById(R.id.rvTempText);
        }
    }

    public RvAdapter(ArrayList<String> dates, ArrayList<String> weathers, ArrayList<String> temps) {
        this.dates = dates;
        this.weathers = weathers;
        this.temps = temps;
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_custom, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dateTv.setText(dates.get(position));
        holder.weatherTv.setText(weathers.get(position));
        holder.tempTv.setText(temps.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
