package com.example.s.maptesttask;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class MainPresenterImpl implements MainContract.MainPresenter {

    private static final String TAG = "MainPresenterImpl";
    private MainContract.MainView mMainView;
    private Context context;
    private Location mLocation;
    private static MainPresenterImpl presenter = new MainPresenterImpl();


    private MainPresenterImpl() {

    }

    // TODO: 26.08.2018 сделать его потокобезопасным
    public static MainPresenterImpl getPresenter() {
        return presenter;
    }


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
        // TODO: 27.08.2018  ЧТО ДЕЛАТЬ ДАЛЬШЕ, И ОБНОВИТ ЛИ НАСТРОЙКИ ПРОВАЙДЕРА ЭТА ТЕМА, И КАК ПРОВЕРИТЬ, БУДЕТ ЛИ ОНО ВЫСЛЕЖИВАТЬ, ИЛИ НЕТ?
        // mMainView.showUpdateLocation();
    }

    @Override
    public void updateLocation(Location updateLocation, String check) {
        mMainView.showUpdateLocation(check);
    }

    @Override
    public void setMainView(MainContract.MainView mainView) {
        mMainView = mainView;
    }


}
