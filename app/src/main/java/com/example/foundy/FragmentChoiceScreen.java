package com.example.foundy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.foundy.Fragments.HomeFragment;
import com.example.foundy.Fragments.ProfileFragment;
import com.example.foundy.Fragments.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentChoiceScreen extends AppCompatActivity {

    Button lostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choice_screen);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment currentFrag = null;

                switch (item.getItemId()) {
                    case R.id.nav_upload:
                        currentFrag = new UploadFragment();
                        break;
                    case R.id.nav_profile:
                        currentFrag = new ProfileFragment();
                        break;
                    case R.id.nav_home:
                        currentFrag = new HomeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFrag).commit();
                return true;
            }
        });
    }

}