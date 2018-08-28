package com.example.s.maptesttask;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
        void passMetersFromUser(String meters);

        void startService();
        //  LatLng passLatLng(LatLng latLng);
    }

    interface MainView {
        void showUpdateLocation(String message);

        void showMarkerOnMap(LatLng latLng);

        void showToast(String message);

       // void launchService();
        //   void requestForPermissions();
        //    void showSnackBar();
    }


    interface Callback {
        void callBack(float meters);
    }

}
