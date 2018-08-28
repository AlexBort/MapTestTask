package com.example.s.maptesttask;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.quentinklein.slt.TrackerSettings;

public class Utils {

    public static void makeSnackbar(View rootView, String message) {
        Snackbar.make(rootView,
                message, Snackbar.LENGTH_LONG).show();
    }

    public static LatLng convertToLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }


    public static void startService(Context context, float meters) {
        Intent intentService = new Intent(context, DistanceService.class);
        intentService.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
       intentService.putExtra("step", meters);
        context.startService(intentService);
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

    // FIXME: 28.08.2018 ПОТОМ УДАЛИМ!!
    public static void setMarker(LatLng latLng, GoogleMap mGoogleMap) {
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mGoogleMap.addMarker(options);
        mGoogleMap.setMaxZoomPreference(20);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
    }




    public static TrackerSettings getTrackerSettings(float meters) {
        return new TrackerSettings()
                .setUseGPS(true)
                .setUseNetwork(true)
                .setUsePassive(true)
                .setMetersBetweenUpdates(meters);
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

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
