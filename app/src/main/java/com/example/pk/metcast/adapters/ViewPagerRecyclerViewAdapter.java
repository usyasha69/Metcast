package com.example.pk.metcast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pk.metcast.R;
import com.example.pk.metcast.custom_views.CustomViewPagerTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerRecyclerViewAdapter extends RecyclerView.Adapter<ViewPagerRecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> dates;
    private ArrayList<String> weathers;
    private ArrayList<String> temps;

    private Context context;

    public ViewPagerRecyclerViewAdapter(ArrayList<String> dates
            , ArrayList<String> weathers, ArrayList<String> temps, Context context) {
        this.dates = dates;
        this.weathers = weathers;
        this.temps = temps;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = layoutInflater.inflate(R.layout.view_custom_view_pager, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        new ViewWorker(context, dates.get(position), weathers.get(position), temps.get(position), holder)
                .makeView();
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time)
        CustomViewPagerTextView time;
        @BindView(R.id.date)
        CustomViewPagerTextView date;
        @BindView(R.id.weather)
        CustomViewPagerTextView weather;
        @BindView(R.id.temperature)
        CustomViewPagerTextView temperature;
        @BindView(R.id.time_image)
        ImageView timeImage;
        @BindView(R.id.weather_image)
        ImageView weatherImage;
        @BindView(R.id.temperature_image)
        ImageView termImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
