package com.example.s.maptesttask.location_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class DistanceService extends Service {


    public int counter = 0;
    private int count = 0;

//    public DistanceService(Context applicationContext) {
//        super();
//        Log.i("HERE", "service!");
//    }

    public DistanceService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //    startTimer();
        Toast.makeText(this, "GOOD JOB!!", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("EXIT", "onDestroy");
        stopTimerTask();
        Intent broadcastIntent = new Intent("BroadReceiver");
        sendBroadcast(broadcastIntent);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTimer();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        count++;
        if (count < 2) {
            Toast.makeText(this, "GOOD_jOB", Toast.LENGTH_SHORT).show();
        }


        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));
            }
        };
    }

    /**
     * not needed
     */
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