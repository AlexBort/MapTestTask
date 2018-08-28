package com.example.s.maptesttask;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MainPresenterImpl implements MainContract.MainPresenter {

    private static final String TAG = "MainPresenterImpl";
    private MainContract.MainView mMainView;
    private Context mContext;

    private static MainPresenterImpl presenter = new MainPresenterImpl();
    private DistanceModel distanceModel = DistanceModel.getInstance();

    private MainPresenterImpl() {

    }

//    public void initProvider() {
//        locationProvider = new LocationProvider(App.getGlobalContext(), this);
//    }


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
    public void onResume() {
        distanceModel.connectProvider();
    }

    @Override
    public void onPause() {
        distanceModel.disconnectProvider();
    }

    @Override
    public void passMetersFromUser(String meters) {
        float step = Float.parseFloat(meters);
        distanceModel.passMeters(step);
        Log.e(TAG, "passMetersFromUser: " + "");
    }

    @Override
    public void presentMarkerOnMap(LatLng latLng) {
        mMainView.showMarkerOnMap(latLng);
    }

//    @Override
//    public void clickTrackDistance(Location location, String meters, Context context) {
//        LatLng latLng = Utils.convertToLatLng(location);
//        //   Toast.makeText(context, meters, Toast.LENGTH_SHORT).show();
//        //     Utils.startService(context, Float.parseFloat(meters));
//    }
//
//    @Override
//    public void clickTrackDistance(LocationProvider provider, String meters) {
//        provider.setDistance(Float.parseFloat(meters));
//    }


//    @Override
//    public void handleNewLocation(Location location) {
//        Location currentLocation = location;
//        Log.d(TAG, location.toString());
//        LatLng latLng = Utils.convertToLatLng(location);
//
//        mMainView.showMarkerOnMap(latLng);
//
//        initLocation(location);
//        if (previousLocation != currentLocation) {
//            if (checkDistance(previousLocation, currentLocation) >= 0.01) {
//                distance += currentLocation.distanceTo(startLocation);
//                previousLocation = currentLocation;
//            }
//        }
//
//        //     mMainView.showToast("check distance (is it correct) :" + String.valueOf(step));
//
//        if (distance >= step) {
//            startLocation = currentLocation;
//            LatLng latLng1 = Utils.convertToLatLng(startLocation);
//            Utils.startService(mContext, step);
//            //   mMainView.showToast("distance= " + String.valueOf(distance));
//
//            distance = 0;
//        }
//    }
//
//    private float checkDistance(Location startLocation, Location endLocation) {
//        return startLocation.distanceTo(endLocation);
//    }
//
//    private void initLocation(Location location) {
//        if (startLocation == null) {
//            startLocation = location;
//            previousLocation = location;
//        }
//    }


    // TODO: 27.08.2018  ЧТО ДЕЛАТЬ ДАЛЬШЕ, И ОБНОВИТ ЛИ НАСТРОЙКИ ПРОВАЙДЕРА ЭТА ТЕМА, И КАК ПРОВЕРИТЬ, БУДЕТ ЛИ ОНО ВЫСЛЕЖИВАТЬ, ИЛИ НЕТ?
    // mMainView.showUpdateLocation();

    @Override
    public void updateLocation(Location updateLocation, String check) {
        mMainView.showUpdateLocation(check);
    }


}// TODO: 28.08.2018 END CLASS !!!


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

