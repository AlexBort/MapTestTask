package com.example.s.maptesttask;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// FIXME: 28.08.2018 ЕСЛИ СТАТИЧНОСТЬ PRESENTER-A НЕ ПОНАДОБИТСЯ, ТО ПЕРЕПИШЕМ С КОНСТРУКТОРОМ, Т.К. МНОГО РАБОТАЕМ С КОНТЕКСТОМ, А ОН НАМ НУЖЕН!!
public class MainPresenterImpl implements MainContract.MainPresenter, LocationProvider.LocationCallback {

    public static final String PERMISSIONS_LOCATION[] = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = "MainPresenterImpl";
    private MainContract.MainView mMainView;
    private Context mContext;
    private Location mLocation;
    private static MainPresenterImpl presenter = new MainPresenterImpl();
    private LocationProvider locationProvider;
    private float step = 5;
    private float distance = 0;
    private Location startLocation;
    private Location previousLocation;
    private GoogleMap mGoogleMap;

    private MainPresenterImpl() {

    }

    public void initProvider() {
        locationProvider = new LocationProvider(App.getGlobalContext(), this);
    }

    // TODO: 26.08.2018 сделать его потокобезопасным
    public static MainPresenterImpl getPresenter() {
        return presenter;
    }

    @Override
    public void setMainView(MainContract.MainView mainView) {
        mMainView = mainView;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void connectProvider() {
        locationProvider.connect();
    }

    @Override
    public void disconnectProvider() {
        locationProvider.disconnect();
    }


    @Override
    public void handleNewLocation(Location location) {
        Location currentLocation = location;
        Log.d(TAG, location.toString());
        LatLng latLng = Utils.convertToLatLng(location);

        // FIXME: 28.08.2018 и надо понять, где ставить эту строчку. В конце этого метода, или в начале!!
        mMainView.showMarkerOnMap(latLng);

        initLocation(location);
        if (previousLocation != currentLocation) {
            if (checkDistance(previousLocation, currentLocation) >= 0.01) {
                distance += currentLocation.distanceTo(startLocation);
                previousLocation = currentLocation;
            }
        }

        if (distance >= step) {
            startLocation = currentLocation;
            LatLng latLng1 = Utils.convertToLatLng(startLocation);
            mMainView.showToast("distance= " + String.valueOf(distance));
            distance = 0;
        }
        // FIXME: 28.08.2018 в MainActivity тоже надо будет передать!!
        // Utils.setMarker();
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


    @Override
    public void clickTrackDistance(Location location, String meters, Context context) {
        LatLng latLng = Utils.convertToLatLng(location);
        //   Toast.makeText(context, meters, Toast.LENGTH_SHORT).show();
        mLocation = location;
        Utils.startService(context, Float.parseFloat(meters));
    }

    @Override
    public void clickTrackDistance(LocationProvider provider, String meters) {
        provider.setDistance(Float.parseFloat(meters));
    }
    // TODO: 27.08.2018  ЧТО ДЕЛАТЬ ДАЛЬШЕ, И ОБНОВИТ ЛИ НАСТРОЙКИ ПРОВАЙДЕРА ЭТА ТЕМА, И КАК ПРОВЕРИТЬ, БУДЕТ ЛИ ОНО ВЫСЛЕЖИВАТЬ, ИЛИ НЕТ?
    // mMainView.showUpdateLocation();

    @Override
    public void updateLocation(Location updateLocation, String check) {
        mMainView.showUpdateLocation(check);
    }


}// TODO: 28.08.2018 END CLASS !!!


//    @SuppressLint("MissingPermission")
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mGoogleMap = googleMap;
//        if (checkLocation()) {
//            mGoogleMap.setMyLocationEnabled(true);
//
//            // FIXME: 27.08.2018  27.08.2018 если оно с такой логикой не будет работать, переделаем!!
//            if (!Utils.isLocationEnabled(App.getGlobalContext()))
//                mMainView.showSnackBar();
//            // FIXME: 27.08.2018 по-ходу оно вообще очень криво работает!!
//        }
//    }

//    private boolean checkLocation() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            // FIXME: 28.08.2018 тут из-за того, что context, как входящий, могут быть проблемы!!
//            if (Utils.isPermissionGranted(App.getGlobalContext(), PERMISSIONS_LOCATION)) {
//                // Permission Granted Already
//                return true;
//            }
//            // Request Permission
//
//            mMainView.requestForPermissions();
//        } else {
//            return true;
//        }
//        return false;
//    }
//}


// TODO: 28.08.2018 КОНСТРУКТОРЫ, КОТОРЫМИ Я РАНЬШЕ ПОЛЬЗОВАЛСЯ!!
//    public MainPresenterImpl(MainContract.MainView mainView, Context context) {
//        this.mainView = mainView;
//        this.context = context;
//    }
//
//    public MainPresenterImpl(MainContract.MainView mainView) {
//        this.mainView = mainView;
//    }
//
//    public MainPresenterImpl(Context context) {
//        this.context = context;
//    }

