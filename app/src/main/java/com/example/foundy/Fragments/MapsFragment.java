package com.example.foundy.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foundy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapsFragment extends Fragment {



    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private double mCurrentLong;
    private double mCurrentLat;




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

            GoogleMap map;

             map = googleMap;

             currentLocation = new LatLng(mCurrentLat, mCurrentLong);

            googleMap.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .title("Item Lost Location")
                    .draggable(true));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

            map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
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
    }

    public String getMarkerLocation() throws IOException {
        Address address;
        Geocoder geocoder = new Geocoder(this.getContext());
        address = geocoder.getFromLocation(mCurrentLat, mCurrentLong, 1).get(0);

        return address.getLocality() + ", " + address.getAdminArea();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_map, container, false);
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