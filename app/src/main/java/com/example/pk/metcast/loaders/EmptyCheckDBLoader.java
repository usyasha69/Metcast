package com.example.pk.metcast.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.pk.metcast.database.DBWorker;


public class EmptyCheckDBLoader extends AsyncTaskLoader<Boolean> {

    public EmptyCheckDBLoader(Context context) {
        super(context);
    }

    @Override
    public Boolean loadInBackground() {
        return new DBWorker().emptyCheckedDB(getContext());
    }
}
