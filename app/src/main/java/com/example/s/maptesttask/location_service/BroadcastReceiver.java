package com.example.s.maptesttask.location_service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(BroadcastReceiver.class.getSimpleName(), "Service Stops! ");
        context.startService(new Intent(context, DistanceService.class));
    }

}