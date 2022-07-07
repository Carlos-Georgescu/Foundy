package com.example.foundy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foundy.Fragments.MapsFragment;
import com.example.foundy.uploadActivities.UploadLost;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;


public class MapActivity extends AppCompatActivity{
    private FusedLocationProviderClient fusedLocationClient;
    MapsFragment mapsFragment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button mDoneButton;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mDoneButton = findViewById(R.id.doneMapButton);

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );

        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            addFragment(location.getLatitude(), location.getLongitude());
                        }
                    }
                });


        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String location = mapsFragment.getMarkerLocation();
                    Intent i = new Intent(MapActivity.this, UploadLost.class);
                    i.putExtra("location", location);
                    startActivity(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void addFragment(double lat, double longi){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mapsFragment = new MapsFragment();
        mapsFragment.setLatAndLong(lat, longi);
        fragmentTransaction.add(R.id.googleMapView, mapsFragment);
        fragmentTransaction.commit();
    }
}