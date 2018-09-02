package com.example.s.maptesttask;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.example.s.maptesttask.model.DistanceModel;
import com.example.s.maptesttask.utils.AndroidUtils;

public class App extends Application {

    public static final int PERMISSION_REQUEST_CODE_ACTIVITY = 1101;
    public static final String PERMISSIONS_LOCATION[] = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static Context context;
    public static final String SERVICE_ID = "exampleService";
    public static float distance = 0;
    public static String flagNotif = "false";
    private Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        DistanceModel distanceModel = DistanceModel.getInstance();
        distanceModel.connectProvider();
        //    activity = MainActivity.class.getClass().getComponentType().;

        //   boolean checkPermission = checkLocation();

    }

    public static Context getGlobalContext() {
        return context;
    }

    public static Resources getAppResources() {
        return context.getResources();
    }

    private boolean checkLocation() {
        if (Build.VERSION.SDK_INT >= 23 && MainActivity.activity != null) {
            if (AndroidUtils.isPermissionGranted(getApplicationContext(), PERMISSIONS_LOCATION)) {
                // Permission Granted Already
                return true;
            }
            // Request Permission
            ActivityCompat.requestPermissions(MainActivity.activity, PERMISSIONS_LOCATION, PERMISSION_REQUEST_CODE_ACTIVITY);
        } else {
            return true;
        }
        return false;
    }

}
