package com.example.s.maptesttask.mvp;

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
        Log.e(TAG, "passMetersFromUser: " + meters);
    }

    @Override
    public void presentMarkerOnMap(LatLng latLng) {
        mMainView.showMarkerOnMap(latLng);
    }

}// TODO: 28.08.2018 END CLASS !!!


