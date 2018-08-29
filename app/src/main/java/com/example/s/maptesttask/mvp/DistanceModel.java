package com.example.s.maptesttask.mvp;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.s.maptesttask.App;
import com.example.s.maptesttask.location_service.LocationProvider;
import com.example.s.maptesttask.utils.AndroidUtils;
import com.example.s.maptesttask.utils.LocationUtils;
import com.google.android.gms.maps.model.LatLng;

public class DistanceModel implements LocationProvider.LocationCallback, MainContract.DistanceCallBack {

    private static final String TAG = "DistanceModel";

    private static DistanceModel instance = new DistanceModel();

    private LocationProvider provider = new LocationProvider(App.getGlobalContext(), this);

    private float step = 0;
    private float distance = 0;
    private Location startLocation;
    private Location previousLocation;


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

        //     mMainView.showToast("check distance (is it correct) :" + String.valueOf(step));

        if (distance >= App.distance) {
            startLocation = currentLocation;
            LatLng latLng1 = LocationUtils.convertToLatLng(startLocation);
            //   App.distance = step;
            AndroidUtils.startService(App.getGlobalContext());
            // FIXME: 29.08.2018 проверить, или оно действительно обновляяет локацию на мапе!!
            presenter.presentMarkerOnMap(latLng1);
            Toast.makeText(App.getGlobalContext(), String.valueOf(App.distance), Toast.LENGTH_SHORT).show();
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

    @Override
    public void initLocation(Location location) {
        if (startLocation == null) {
            startLocation = location;
            previousLocation = location;
        }
    }


}
