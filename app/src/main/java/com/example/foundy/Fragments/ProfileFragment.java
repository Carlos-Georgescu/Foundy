package com.example.foundy.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foundy.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        Button uploadLost;

        uploadLost = view.findViewById(R.id.uploadLost);

        uploadLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment map = new UploadFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "found");
                map.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, map).commit();
            }
        });

        return view;
    }
}