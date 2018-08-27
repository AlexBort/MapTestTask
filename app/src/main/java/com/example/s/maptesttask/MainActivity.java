package com.example.s.maptesttask;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
        /*, LocationListener*/, LocationProvider.LocationCallback, MainContract.MainView {

    public static final int PERMISSION_REQUEST_CODE_ACTIVITY = 1101;
    public static final String PERMISSIONS_LOCATION[] = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String TAG = "MainActivity";
    private GoogleMap mGoogleMap;
    private LocationProvider locationProvider;
    SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient providerClient;
    @BindView(R.id.root_linear)
    LinearLayout rootLinear;
    private MainPresenterImpl presenter;
    private MainPresenterImpl presenterUpdate;
    private Location currentLocation;
    @BindView(R.id.edit_distance)
    EditText editMeters;
    LocationRequest locationRequest;

//    private LocationManager locationManager;
//    private LocationListener locationListener;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        if (GoogleServiceUtils.isGoogleServiceAvailable(this)) {
//            GoogleServiceUtils.getLocationPermission(this);
//        }
        locationProvider = new LocationProvider(this, this);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        locationProvider.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationProvider.disconnect();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mGoogleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);
            // Check if we were successful in obtaining the map.
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (checkLocation()) {
            mGoogleMap.setMyLocationEnabled(true);

            if (!Utils.isLocationEnabled(this))
                Utils.makeSnackbar(rootLinear, Constants.snackMessage);
            else Toast.makeText(this, "TEST DEBUG", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        LatLng latLng = Utils.convertToLatLng(location);
        Utils.setMarker(latLng, mGoogleMap);
    }


    private boolean checkLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (isPermissionGranted(getApplicationContext(), PERMISSIONS_LOCATION)) {
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

    public static boolean isPermissionGranted(Context context, String[] permissions) {
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that mGoogleMap is not null.
     */
    private void setUpMap() {
        LatLng latLng = new LatLng(0, 0);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void trackDistance(View view) {
        String meters = editMeters.getText().toString();
        Utils.hideKeyBoard(this, view);
        presenter.clickTrackDistance(locationProvider, meters);
        //   locationProvider.setDistance(Float.parseFloat(meters));
    }

    @Override
    public void showUpdateLocation(String message) {

    }
}
