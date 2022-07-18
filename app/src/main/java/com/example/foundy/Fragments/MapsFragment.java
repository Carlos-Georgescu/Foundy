package com.example.foundy.Fragments;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foundy.FragmentChoiceScreen;
import com.example.foundy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

public class MapsFragment extends Fragment {



    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private double mCurrentLong;
    private double mCurrentLat;
    private FusedLocationProviderClient mFusedLocationClient;
    MapsFragment mMapsFragment;
    GoogleMap mMap;
    Marker mCurrentMarker;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng currentLocation;



            mMap = googleMap;

             currentLocation = new LatLng(mCurrentLat, mCurrentLong);

            mCurrentMarker= googleMap.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .title("Item Lost Location")
                    .draggable(true));

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(@NonNull Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(@NonNull Marker marker) {
                    mCurrentLat = marker.getPosition().latitude;
                    mCurrentLong = marker.getPosition().longitude;
                }

                @Override
                public void onMarkerDragStart(@NonNull Marker marker) {

                }
            });
        }
    };


    public void setLatAndLong(double lat, double longitude){
        mCurrentLat = lat;
        mCurrentLong = longitude;
        LatLng currPos = new LatLng(mCurrentLat, mCurrentLong);
        if(mCurrentMarker != null)
            mCurrentMarker.setPosition(currPos);
    }

    public String getMarkerLocation() throws IOException {
        Address address;
        Geocoder geocoder = new Geocoder(this.getContext());
        address = geocoder.getFromLocation(mCurrentLat, mCurrentLong, 1).get(0);

        return address.getLocality() + ", " + address.getAdminArea();
    }


    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        Button doneButton;
        View rootView =  inflater.inflate(R.layout.activity_map, container, false);
        doneButton = rootView.findViewById(R.id.doneMapButton);




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



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.getResult() != null) {
                    setLatAndLong(task.getResult().getLatitude(), task.getResult().getLongitude());
                }
            }
        });


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String location = getMarkerLocation();

                    Bundle bundle = new Bundle();
                    bundle.putString("location", location);
                    Fragment fragment = new UploadFragment();
                    fragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}