package com.example.s.maptesttask;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.example.s.maptesttask.model.DistanceModel;
import com.example.s.maptesttask.utils.AndroidUtils;
import com.example.s.maptesttask.utils.Constants;

public class App extends Application {


    private static Context context;
    static String SHARED_NAME = "STEP_SHARED";
    public static String FLOAT_KEY = "STEP";


    public static SharedPreferences PREFERENCES;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        DistanceModel distanceModel = DistanceModel.getInstance();

        PREFERENCES = this.getSharedPreferences(SHARED_NAME, 0);


    }

    public static Context getGlobalContext() {
        return context;
    }

    public static Resources getAppResources() {
        return context.getResources();
    }

}
