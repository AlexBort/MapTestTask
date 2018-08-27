package com.example.s.maptesttask;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import fr.quentinklein.slt.LocationTracker;

public class DistanceService extends Service {

    private static final int PERM_REQUEST_LOCATION = 1;
    private static final String TAG = "DistanceService";
    public static final int RESULT_OK = -1;
    public static final String KEY_MESSAGE = "KEY_MESSAGE";
    public static final String KEY_RECEIVER = "KEY_RECEIVER";
    int temp = 0;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind Called", Toast.LENGTH_SHORT).show();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public DistanceService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " + "onStartCommand");
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.e(TAG, "onStartCommand: " + "Received Start Foreground Intent");

//            temp++;
//            if (temp == 1)
//                Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show();
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

        Notification notification = Utils.createNotification(this, Constants.TITLE_NOTIF, Constants.DESCRIP_NOTIF, pendingIntent);

        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification);

        trackDistance(meters);

        //    trackDistance(meters);
        DistanceTask distanceTask = new DistanceTask(this, meters, pendingIntent);
        distanceTask.execute();

        // TODO: 25.08.2018 это когда мы посчитаем необходимое нам расстояние

    }


    @SuppressLint("MissingPermission")
    private void trackDistance(float meters) {
        LocationTracker tracker = new LocationTracker(this, Utils.getTrackerSettings(meters)) {
            @Override
            public void onLocationFound(@NonNull Location location) {
                Toast.makeText(DistanceService.this,
                        String.valueOf(location.getLongitude()),
                        Toast.LENGTH_SHORT).show();
                // TODO: 26.08.2018 ПЕРЕДАТЬ НОВУЮ ЛОКАЦИЮ В MAIN_ACTIVITY - отобразить ее на карте!!
                // и начать расчет дистанции с новой локации!!
                //  stopListening();
            }

            @Override
            public void onTimeout() {

            }
        };

        tracker.startListening();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: 25.08.2018  надо понять, надо ли оно здесь
        //    stopSelf();
    }

}
