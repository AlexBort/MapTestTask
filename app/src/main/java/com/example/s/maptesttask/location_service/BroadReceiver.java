package com.example.s.maptesttask.location_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(BroadReceiver.class.getSimpleName(), "Service Stops! ");

        context.startService(new Intent(context, NewService.class));
    }

}