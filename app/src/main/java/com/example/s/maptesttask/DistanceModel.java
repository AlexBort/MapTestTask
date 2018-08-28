package com.example.s.maptesttask;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class DistanceModel implements LocationProvider.LocationCallback {

    private static final String TAG = "DistanceModel";

    private static DistanceModel instance = new DistanceModel();
    private MainPresenterImpl presenter = MainPresenterImpl.getPresenter();
    private LocationProvider provider = new LocationProvider(App.getGlobalContext(), this);

    private float step = 5;
    private float distance = 0;
    private Location startLocation;
    private Location previousLocation;

    private DistanceModel() {

    }

    public static DistanceModel getInstance() {
        return instance;
    }


    @Override
    public void handleNewLocation(Location location) {
        Location currentLocation = location;
        Log.d(TAG, location.toString());
        LatLng latLng = Utils.convertToLatLng(location);

        presenter.presentMarkerOnMap(latLng);

        initLocation(location);
        if (previousLocation != currentLocation) {
            if (checkDistance(previousLocation, currentLocation) >= 0.01) {
                distance += currentLocation.distanceTo(startLocation);
                previousLocation = currentLocation;
            }
        }

        //     mMainView.showToast("check distance (is it correct) :" + String.valueOf(step));

        if (distance >= step) {
            startLocation = currentLocation;
            LatLng latLng1 = Utils.convertToLatLng(startLocation);
            Utils.startService(App.getGlobalContext(), step);
            presenter.presentMarkerOnMap(latLng);
            //   mMainView.showToast("distance= " + String.valueOf(distance));

            distance = 0;
        }
    }

    public void connectProvider() {
        provider.connect();
    }

    public void disconnectProvider() {
        provider.disconnect();
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

    public void passMeters(float meters) {
        step = meters;
    }

}
