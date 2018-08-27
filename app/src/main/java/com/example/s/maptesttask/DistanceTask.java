package com.example.s.maptesttask;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

public class DistanceTask extends AsyncTask<Void, Void, Void> {

    private float meters;

    // TODO: 26.08.2018 что-то придумать, чтобы не было утечки памяти!!
    private Context context;
    private Location mLocation;
    private PendingIntent pendingIntent;

    public DistanceTask(Context context, float meters, PendingIntent pendingIntent) {
        this.context = context;
        this.meters = meters;
        this.pendingIntent = pendingIntent;
    }


    @SuppressLint("MissingPermission")
    @Override
    protected Void doInBackground(Void... voids) {

        // ТРЕКИНГ ДИСТАНЦИИ И
     /*   LocationTracker tracker = new LocationTracker(context, Utils.getTrackerSettings(meters)) {
            @Override
            public void onLocationFound(@NonNull Location location) {
                // TODO: 26.08.2018 ПЕРЕДАТЬ НОВУЮ ЛОКАЦИЮ В MAIN_ACTIVITY - отобразить ее на карте!!
                // и начать расчет дистанции с новой локации!!
                mLocation = location;
            }

            @Override
            public void onTimeout() {

            }
        };
        tracker.startListening(); // FIXME: 26.08.2018 Can't create handler inside thread that has not called Looper.prepare()*/


        Notification notifResult = Utils.createNotification(context, Constants.TITLE_NOTIF, Constants.RESULT_NOTIF, pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notifResult);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        MainPresenterImpl presenter = MainPresenterImpl.getPresenter();
        presenter.updateLocation(null, "WORK!");

    }


}
