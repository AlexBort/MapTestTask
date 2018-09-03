package com.example.s.maptesttask.presenter;

import android.content.Context;
import android.util.Log;

import com.example.s.maptesttask.App;
import com.example.s.maptesttask.location_service.DistanceService;
import com.example.s.maptesttask.model.DistanceModel;
import com.example.s.maptesttask.mvp.MainContract;
import com.example.s.maptesttask.utils.AndroidUtils;
import com.example.s.maptesttask.utils.Constants;
import com.google.android.gms.maps.model.LatLng;

import butterknife.internal.Utils;

public class MainPresenterImpl implements MainContract.MainPresenter {

    private static final String TAG = "MainPresenterImpl";
    private MainContract.MainView mMainView;
    private Context mContext;

    private static MainPresenterImpl presenter = new MainPresenterImpl();
    private DistanceModel distanceModel = DistanceModel.getInstance();
    private DistanceService service = new DistanceService();


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
    public Context getContext() {
        if (mContext != null)
            return mContext;
        return null;
    }


    @Override
    public void onPause() {
        distanceModel.disconnectProvider();
    }

    @Override
    public void onCreate() {
        distanceModel.connectProvider();
    }

    @Override
    public void onResume() {
        distanceModel.connectProvider();
    }

    @Override
    public void passMetersFromUser(String meters) {
        float step = Float.parseFloat(meters);
        App.PREFERENCES.edit().putFloat(Constants.FLOAT_KEY, step).commit();
        AndroidUtils.startService(getContext());

    }

    @Override
    public void presentMarkerOnMap(LatLng latLng) {
        if (mMainView != null && getContext() != null)
            mMainView.showMarkerOnMap(latLng);

    }


}


