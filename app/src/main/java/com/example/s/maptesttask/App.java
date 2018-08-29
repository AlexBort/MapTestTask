package com.example.s.maptesttask;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.example.s.maptesttask.mvp.DistanceModel;

public class App extends Application {

    private static Context context;
    public static final String SERVICE_ID = "exampleService";


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
       DistanceModel distanceModel = DistanceModel.getInstance();


    }

    public static Context getGlobalContext() {
        return context;
    }

    public static Resources getAppResources() {
        return context.getResources();
    }


}
