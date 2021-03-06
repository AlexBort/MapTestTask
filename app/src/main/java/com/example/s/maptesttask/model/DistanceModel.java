package com.example.s.maptesttask.model;

import android.location.Location;
import android.util.Log;
import com.example.s.maptesttask.App;
import com.example.s.maptesttask.location_service.LocationProvider;
import com.example.s.maptesttask.presenter.MainPresenterImpl;
import com.example.s.maptesttask.utils.AndroidUtils;
import com.example.s.maptesttask.utils.Constants;
import com.example.s.maptesttask.utils.LocationUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;


/*In the beginning i tried to track the location using DistanceModel.
 But for more convenient work (switching connection and work in background)
  i passed logic of tracking location into the Service */


public class DistanceModel implements LocationProvider.LocationCallback {

    private static final String TAG = "DistanceModel";


    private static DistanceModel instance = new DistanceModel();

    private LocationProvider locationProvider = new LocationProvider(App.getGlobalContext(), this);

    private float step = 0;
    private float distance = 0;
    private Location startLocation;
    private Location previousLocation;
    private Timer timer;


    private DistanceModel() {

    }

    public static DistanceModel getInstance() {
        return instance;
    }

    private static MainPresenterImpl presenter = MainPresenterImpl.getPresenter();


    @Override
    public void handleNewLocation(Location location) {
        Location currentLocation = location;
        Log.d(TAG, location.toString());
        LatLng latLng = LocationUtils.convertToLatLng(location);

        presenter.presentMarkerOnMap(latLng);

        initLocation(location);
        if (previousLocation != currentLocation) {
            if (checkDistance(previousLocation, currentLocation) >= 0.01) {
                distance += currentLocation.distanceTo(startLocation);
                previousLocation = currentLocation;
            }
        }

        if (distance >= step && step != 0) {
            startLocation = currentLocation;
            LatLng latLng1 = LocationUtils.convertToLatLng(startLocation);
            App.PREFERENCES.edit().putFloat(Constants.FLOAT_KEY, step).commit();
            AndroidUtils.startService(App.getGlobalContext());
            presenter.presentMarkerOnMap(latLng1);
            distance = 0;
        }
    }

    public void connectProvider() {
        locationProvider.connect();

    }

    public void disconnectProvider() {
        locationProvider.disconnect();
    }


    private float checkDistance(Location startLocation, Location endLocation) {
        return startLocation.distanceTo(endLocation);
    }


    private void initLocation(Location location) {
        if (startLocation == null) {
            startLocation = location;
            previousLocation = location;
        }
    }


    public void initStep(float stepUser) {
        step = stepUser;
    }
}
