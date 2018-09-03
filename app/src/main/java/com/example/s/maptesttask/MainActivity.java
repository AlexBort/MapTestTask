package com.example.s.maptesttask;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.s.maptesttask.presenter.MainPresenterImpl;
import com.example.s.maptesttask.utils.AndroidUtils;
import com.example.s.maptesttask.mvp.MainContract;
import com.example.s.maptesttask.utils.LocationUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        MainContract.MainView {


    public static final int PERMISSION_REQUEST_CODE_ACTIVITY = 1101;
    public static final String PERMISSIONS_LOCATION[] = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = "MainActivity";
    private GoogleMap mGoogleMap;
    private SupportMapFragment supportMapFragment;
    @BindView(R.id.root_linear)
    LinearLayout rootLinear;
    private MainPresenterImpl presenter;
    @BindView(R.id.edit_distance)
    EditText editMeters;
    public static AppCompatActivity activity;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        activity = this;

        initPresenter();
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        //  presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //   presenter.onPause();
    }

    private void setUpMapIfNeeded() {
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (checkLocation()) {
            mGoogleMap.setMyLocationEnabled(true);
            //  AndroidUtils.gpsNetToastNotif(this);
        }

    }


    private boolean checkLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (AndroidUtils.isPermissionGranted(getApplicationContext(), PERMISSIONS_LOCATION)) {
                // Permission Granted Already
                return true;
            }
            // Request Permission
            requestPermissions(PERMISSIONS_LOCATION, PERMISSION_REQUEST_CODE_ACTIVITY);
        } else {
            return true;
        }
        return false;
    }


    public void clickTrackerDistance(View view) {
        String meters = editMeters.getText().toString();
        presenter.passMetersFromUser(meters);
        AndroidUtils.hideKeyBoard(this, view);
    }


    @Override
    public void showMarkerOnMap(LatLng latLng) {
        if (latLng != null)
            LocationUtils.setMarker(latLng, mGoogleMap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //       presenter.connectLocation();
    }

    private void initPresenter() {
        presenter = MainPresenterImpl.getPresenter();
        presenter.setMainView(this);
        presenter.setContext(this);
    }
}
