package com.example.s.maptesttask;

import android.content.Context;
import android.location.Location;

public interface MainContract {

    interface MainPresenter {
        void clickTrackDistance(Location location, String meters, Context context);

        void clickTrackDistance(LocationProvider provider, String meters);

        void updateLocation(Location updateLocation, String check);

        void setMainView(MainView mainView);
        //  LatLng passLatLng(LatLng latLng);
    }

    interface MainView {
        void showUpdateLocation(String message);
    }


    interface Callback {
        void callBack(float meters);
    }

}
