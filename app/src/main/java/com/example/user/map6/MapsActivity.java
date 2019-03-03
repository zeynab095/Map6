package com.example.user.map6;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener, GoogleMap.OnMarkerDragListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;
    Marker marker;
    private GoogleMap mMap;

    Double lat;
    Double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///////////////////////////////////////////////////

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermission();

        provider = locationManager.getBestProvider(new Criteria(), true);


        if (provider == null) {
            Log.i("hi", "No location provider found!");
            return;
        } else Log.i("hi", "These is provider");


        checkLocationPermission();
        Log.i("check", String.valueOf(checkLocationPermission()));

        Log.i("a", "Aa");
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {

            Log.i("Location info", "Location achieved");
            lat = location.getLatitude();
            lng = location.getLongitude();
        } else{ Log.i("Location info", "Location failed");

            lat = 40.413048;
            lng =   49.842140;

        }




        //////////////////////////////////////////////////
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    protected void onResume() {

        super.onResume();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        checkLocationPermission();

      // mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lng);

         marker = mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .draggable(true));
       // marker.setDraggable(true);
        mMap.setOnMarkerDragListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }

    @Override
    public void onLocationChanged(Location location) {



    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    ////////////////////////////////////////////////////////////
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("hey")
                        .setMessage("allow")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                     //   locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
        }
    }

    public void setDestination(View view) {


         Log.i("main","works");
        // Log.i("A", String.valueOf(mMap.getMyLocation().getLatitude())+"  "+ mMap.getMyLocation().getLongitude());
        marker.getPosition();

        Intent intent = new Intent(this, SetAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putFloat("desLat", (float) marker.getPosition().latitude);
        bundle.putFloat("desLong", (float) marker.getPosition().longitude);

        intent.putExtras(bundle);
        // startActivity(intent);
        startActivity(intent);


    }

    @Override
    public void onMarkerDragStart(Marker marker) {

        lat = marker.getPosition().latitude;
        lng = marker.getPosition().longitude;
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
