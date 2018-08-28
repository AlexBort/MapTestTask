package com.example.s.maptesttask;

import android.content.Context;
import android.location.Location;

public interface MainContract {

    interface MainPresenter {
        void clickTrackDistance(Location location, String meters, Context context);

        void clickTrackDistance(LocationProvider provider, String meters);

        void updateLocation(Location updateLocation, String check);

        void setMainView(MainView mainView);

        void initProvider();

        void setContext(Context context); // TODO: 28.08.2018 МОЖЕТ УБРАТЬ ЭТОТ МЕТОД, ПО НЕНАДОБНОСТИ!!

        void connectProvider();

        void disconnectProvider();
        //  LatLng passLatLng(LatLng latLng);
    }

    interface MainView {
        void showUpdateLocation(String message);
        //   void requestForPermissions();
        //    void showSnackBar();
    }


    interface Callback {
        void callBack(float meters);
    }

}
