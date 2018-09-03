package com.example.s.maptesttask.location_service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.s.maptesttask.App;
import com.example.s.maptesttask.MainActivity;
import com.example.s.maptesttask.presenter.MainPresenterImpl;
import com.example.s.maptesttask.utils.AndroidUtils;
import com.example.s.maptesttask.utils.Constants;
import com.example.s.maptesttask.utils.LocationUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

public class DistanceService extends Service implements LocationProvider.LocationCallback {


    private float step = 0;
    private float distance = 0;
    private Location startLocation;
    private Location previousLocation;


    private Timer timer;
    private final String TAG = "DistanceService";

    private MainPresenterImpl presenter = MainPresenterImpl.getPresenter();
    private LocationProvider locationProvider = new LocationProvider(App.getGlobalContext(), this);


    public DistanceService(Context applicationContext) {
        super();
        Log.i(TAG, "service!");
    }

    public DistanceService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        stopTimerTask();

        // it helps me make service not killed
        Intent broadcastIntent = new Intent("BroadcastReceiver");
        sendBroadcast(broadcastIntent);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();

        timer.schedule(timerTask, 5000, 5000);

    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            connectProvider();
        }
    };


    private void showNotification(float step) {

        Log.e(TAG, "showNotification: " + "ЗАШЛО!!");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
                notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Notification notification = AndroidUtils.createNotification(this,
                Constants.TITLE_NOTIF, Constants.RESULT_NOTIF + " " + String.valueOf(step) + " " +
                        Constants.METERS, pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

        disconnectProvider();
        AndroidUtils.startService(App.getGlobalContext());
    }


    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // listener for getting update location
    @Override
    public void handleNewLocation(Location location) {
        Location currentLocation = location;
        Log.d(TAG, location.toString());
        LatLng latLng = LocationUtils.convertToLatLng(location);

        // show new location on Map
        presenter.presentMarkerOnMap(latLng);

        step = App.PREFERENCES.getFloat(App.FLOAT_KEY, 0);
        Log.e(TAG, "handleNewLocation: st" + String.valueOf(step));


        initLocation(location);
        if (previousLocation != currentLocation) {
            if (checkDistance(previousLocation, currentLocation) >= 0.01) {
                distance += currentLocation.distanceTo(startLocation);
                previousLocation = currentLocation;
            }
        }


        locationProvider.disconnect();
        locationProvider.connect();

        // if travelled distance equals distance specified by user, we will show notification
        if (distance >= step && step != 0) {

            startLocation = currentLocation;
            LatLng latLng1 = LocationUtils.convertToLatLng(startLocation);

            // show new location on Map
            presenter.presentMarkerOnMap(latLng1);

            distance = 0;
            showNotification(step);
        }
    }

    private void initLocation(Location location) {
        if (startLocation == null) {
            startLocation = location;
            previousLocation = location;
        }
    }

    private float checkDistance(Location startLocation, Location endLocation) {
        return startLocation.distanceTo(endLocation);
    }

    public void connectProvider() {
        locationProvider.connect();

    }

    public void disconnectProvider() {
        locationProvider.disconnect();
    }

    // i used this method for the checking work of service in background and also when we closed the app
    private void checkWorkingService(String steps) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
                notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Notification notification = AndroidUtils.createNotification(this,
                Constants.TITLE_CHECK_SERVICE, steps, pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


}