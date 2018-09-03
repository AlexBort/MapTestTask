package com.example.s.maptesttask.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.s.maptesttask.App;
import com.example.s.maptesttask.R;
import com.example.s.maptesttask.location_service.DistanceService;

public class AndroidUtils {

    public static void makeSnackbar(View rootView, String message) {
        Snackbar.make(rootView,
                message, Snackbar.LENGTH_LONG).show();
    }

    public static void startService(Context context/*, float meters*/) {
        DistanceService service = new DistanceService(context);
        Intent intentService = new Intent(context, service.getClass());
        intentService.putExtra(App.FLOAT_KEY, 3);
        context.startService(intentService);
    }

    public static void gpsNetToastNotif(/*boolean flagNotif, */Context context) {
        // if (flagNotif) {
        Toast.makeText(context, Constants.SWITCH_NOTIF, Toast.LENGTH_SHORT).show();
        return;
        //  }
    }

    public static void hideKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Notification createNotification(Context context, String title, String description, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.
                setContentTitle(title).
                setContentText(description).
                setTicker(" ").
                setSmallIcon(R.mipmap.ic_launcher).
                setAutoCancel(true).
                setContentIntent(pendingIntent).build();
        return notification;
    }

    public static boolean isPermissionGranted(Context context, String[] permissions) {
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
