package com.example.s.maptesttask;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import com.example.s.maptesttask.utils.AndroidUtils;

// Application class
public class App extends Application {


    private static Context context;
    static String SHARED_NAME = "STEP_SHARED";
    public static String FLOAT_KEY = "STEP";
    public static SharedPreferences PREFERENCES;
    public static final String KEY_INTENT = "keyFloat";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        PREFERENCES = this.getSharedPreferences(SHARED_NAME, 0);
        AndroidUtils.startService(context);


    }

    public static Context getGlobalContext() {
        return context;
    }

    public static Resources getAppResources() {
        return context.getResources();
    }

}
