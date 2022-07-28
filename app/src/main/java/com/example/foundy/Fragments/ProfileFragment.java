package com.example.foundy.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foundy.Adapters.ProfileMatchedAdapter;
import com.example.foundy.MeetupScreen;
import com.example.foundy.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        Button uploadLost;

        TabLayout tabLayout;
        ViewPager2 viewPager;

        uploadLost = view.findViewById(R.id.uploadLost);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);


        ProfileMatchedAdapter profileMatchedAdapter = new ProfileMatchedAdapter(getActivity());
        viewPager.setAdapter(profileMatchedAdapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0)
                    tab.setText("Found Matched");
                else
                    tab.setText("Lost Matched");
            }
        }).attach();

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

        Button uploadLost4;

        uploadLost4 = view.findViewById(R.id.uploadLost4);

        uploadLost4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MeetupScreen.class);
                startActivity(i);
            }
        });


        return view;
    }
}