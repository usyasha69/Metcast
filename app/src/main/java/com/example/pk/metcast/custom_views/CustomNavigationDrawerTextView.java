package com.example.pk.metcast.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomNavigationDrawerTextView extends TextView {

    public CustomNavigationDrawerTextView(Context context) {
        super(context);
        init();
    }

    public CustomNavigationDrawerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomNavigationDrawerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * This method set custom font
     */
    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "simple_round.ttf");
        setTypeface(tf);
    }
}
