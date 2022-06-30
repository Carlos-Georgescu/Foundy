package com.example.foundy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foundy.uploadActivities.UploadLost;

public class ChoiceScreen extends AppCompatActivity {

    Button lostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_screen);

        lostButton = findViewById(R.id.lostItemButton);

        lostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoiceScreen.this, UploadLost.class);
                startActivity(i);
            }
        });
    }
}