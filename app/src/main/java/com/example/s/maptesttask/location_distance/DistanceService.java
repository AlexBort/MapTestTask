package com.example.s.maptesttask.location_distance;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.s.maptesttask.utils.AndroidUtils;
import com.example.s.maptesttask.utils.Constants;
import com.example.s.maptesttask.MainActivity;
import com.example.s.maptesttask.utils.LocationUtils;

public class DistanceService extends Service {

    private static final int PERM_REQUEST_LOCATION = 1;
    private static final String TAG = "DistanceService";
    public static final int RESULT_OK = -1;
    public static final String KEY_MESSAGE = "KEY_MESSAGE";
    public static final String KEY_RECEIVER = "KEY_RECEIVER";
    int temp = 0;
    String metersStr = " ";

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
     //   Toast.makeText(this, "onBind Called", Toast.LENGTH_SHORT).show();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public DistanceService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " + "onStartCommand");
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.e(TAG, "onStartCommand: " + "Received Start Foreground Intent");

            float meters = intent.getFloatExtra(Constants.INTENT_SERVICE_KEY, 0);
            showNotification(meters);

        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.e(TAG, "onStartCommand: " + "Received Stop Foreground Intent");

            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    @SuppressLint("MissingPermission")
    private void showNotification(float meters) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                notificationIntent, 0);

        if (meters != 0)
            metersStr = String.valueOf(meters);

        Notification notification = AndroidUtils.createNotification(this,
                Constants.TITLE_NOTIF, Constants.RESULT_NOTIF + " " + metersStr +
                        Constants.METERS, pendingIntent);

        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification);

        //  Toast.makeText(this, App.SERVICE_ID, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "WORK IN BACKGROUND!", Toast.LENGTH_SHORT).show();

     //   Toast.makeText(this, String.valueOf(Constants.STEP), Toast.LENGTH_SHORT).show();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Constants.STEP != 0) {

            Notification notification2 = AndroidUtils.createNotification(this,
                    Constants.TITLE_NOTIF, Constants.RESULT_NOTIF + " " + metersStr +
                            Constants.METERS, pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification2);
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       }

}
