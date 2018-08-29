package com.example.s.maptesttask.mvp;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface MainContract {

    interface DistanceCallBack {
       // void initLocation(Location location) ;
        }


        interface MainPresenter {

            void setMainView(MainView mainView);

            void setContext(Context context);

            void onResume();

            void onPause();

            void passMetersFromUser(String meters);

            void presentMarkerOnMap(LatLng latLng);

        }

        interface MainView {

            void showMarkerOnMap(LatLng latLng);

            void showToast(String message);

        }


    }
