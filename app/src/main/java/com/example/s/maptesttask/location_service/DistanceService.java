package com.example.s.maptesttask.location_service;

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

import com.example.s.maptesttask.App;
import com.example.s.maptesttask.MainActivity;
import com.example.s.maptesttask.presenter.MainPresenterImpl;
import com.example.s.maptesttask.utils.AndroidUtils;
import com.example.s.maptesttask.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

public class DistanceService extends Service {


    private float step = 0;
    private Timer timer;
    private boolean flagNotification = true;
    private final String TAG = "DistanceService";

    public DistanceService(Context applicationContext) {
        super();
        Log.i("HERE", "service!");
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
        Log.e("EXIT", "onDestroy");
        stopTimerTask();
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
            //  checkWorkingService();
            showNotification();
        }
    };

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


    private void showNotification() {

        step = App.PREFERENCES.getFloat(App.FLOAT_KEY, 0);
//        checkWorkingService(String.valueOf(step));
        //   MainPresenterImpl.getPresenter().showToast(String.valueOf(step));

        if (step != 0) {
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

            App.PREFERENCES.edit().putFloat(App.FLOAT_KEY, 0).commit();
        }
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
}
