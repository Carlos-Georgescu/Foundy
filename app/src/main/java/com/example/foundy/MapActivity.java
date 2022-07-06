package com.example.foundy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.foundy.Fragments.MapsFragment;

public class MapActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        addFragment();
    }

    private void addFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapsFragment mapsFragment = new MapsFragment();
        fragmentTransaction.add(R.id.googleMapView, mapsFragment);
        fragmentTransaction.commit();
    }
}