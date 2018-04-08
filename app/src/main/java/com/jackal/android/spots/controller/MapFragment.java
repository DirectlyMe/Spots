package com.jackal.android.spots.controller;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.jackal.android.spots.R;
import com.jackal.android.spots.model.Spot;
import com.jackal.android.spots.model.SpotSingleton;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback{

    private final static String SPOT_POSITION_ARG = "spot position";

    private int mSpotPosition;

    private GoogleMap mGoogleMap;

    private FusedLocationProviderClient mClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    private Spot mSpot;

    public static MapFragment newInstance(int userPosition) {

        Bundle args = new Bundle();
        args.putSerializable(SPOT_POSITION_ARG, userPosition);

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mSpotPosition = getArguments().getInt(SPOT_POSITION_ARG);
        mSpot = SpotSingleton.get(getActivity()).getSpot(mSpotPosition);

        mClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                mCurrentLocation = locationResult.getLastLocation();
                getMapAsync(MapFragment.this);
            }
        };

        findUserLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        updateUI();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void updateUI(){
        if (mGoogleMap == null) {
            return;
        }
        
        LatLng spotCoordinates = new LatLng(mSpot.getLat(), mSpot.getLon());
        LatLng userCoordinates = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        mGoogleMap.addMarker(new MarkerOptions().position(spotCoordinates).title(mSpot.getTitle()));
        mGoogleMap.addMarker(new MarkerOptions().position(userCoordinates).title("You"));

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(spotCoordinates)
                .include(userCoordinates)
                .build();

        int margin = getResources().getDimensionPixelOffset(R.dimen.map_insert_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        mGoogleMap.animateCamera(update);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        mClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void stopLocationUpdates() {
        mClient.removeLocationUpdates(mLocationCallback);
    }

    private void createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(0);
        locationRequest.setNumUpdates(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest = locationRequest;
    }

    private void findUserLocation() {

        createLocationRequest();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        startLocationUpdates();
    }
}
