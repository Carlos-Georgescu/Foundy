package com.example.foundy.uploadActivities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foundy.ChoiceScreen;
import com.example.foundy.Fragments.MapsFragment;
import com.example.foundy.MapActivity;
import com.example.foundy.R;
import com.example.foundy.Structures.LostItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UploadLost extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;
    EditText setDate;
    Button openMapButton;
    EditText lostItemLocation;
    Button electronic;
    Button jewlery;
    Button clothing;
    Button toys;
    Button office;
    Button other;
    TextView question1;
    TextView question2;
    Button takePictureButton;
    ImageView itemPicture;
    Button helpMeFind;
    EditText whatLostText;
    EditText question1Answer;
    EditText question2Answer;
    LostItem lostItem;
    Uri saveUriPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_lost);

        setDate = findViewById(R.id.selectDateText);
        question1Answer = findViewById(R.id.question1Answer);
        question2Answer = findViewById(R.id.question2Answer);
        itemPicture = findViewById(R.id.itemPicture);
        openMapButton = findViewById(R.id.openMapButton);
        lostItemLocation = findViewById(R.id.lostItemLocation);
        electronic = findViewById(R.id.electronic);
        jewlery = findViewById(R.id.jewlery);
        clothing = findViewById(R.id.clothing);
        toys = findViewById(R.id.toys);
        office = findViewById(R.id.office);
        other = findViewById(R.id.other);
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        takePictureButton = findViewById(R.id.takePictureButton);
        helpMeFind = findViewById(R.id.helpMeFind);
        whatLostText = findViewById(R.id.whatLostText);
        lostItem = new LostItem();


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UploadLost.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth +" / " + month + " / " + year;
                setDate.setText(date);
            }
        };

        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UploadLost.this, MapActivity.class);
                startActivity(i);
            }
        });

        String userLostItemLocation = getIntent().getStringExtra("location");
        lostItemLocation.setText(userLostItemLocation);

        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electronic.setBackgroundColor(Color.LTGRAY);
                question1.setText("What kind of device is it?");
                question2.setText("What model is it?");
                lostItem.setCategory("electronic");
            }
        });

        jewlery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jewlery.setBackgroundColor(Color.LTGRAY);
                question1.setText("How color is it?");
                question2.setText("How much is it worth?");
                lostItem.setCategory("jewlery");
            }
        });

        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clothing.setBackgroundColor(Color.LTGRAY);
                question1.setText("What brand is it?");
                question2.setText("Any standout qualities about it?");
                lostItem.setCategory("clothing");
            }
        });

        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toys.setBackgroundColor(Color.LTGRAY);
                question1.setText("What brand is it?");
                question2.setText("What color is it?");
                lostItem.setCategory("toys");
            }
        });

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                office.setBackgroundColor(Color.LTGRAY);
                question1.setText("How many did you lose?");
                question2.setText("Where are they from?");
                lostItem.setCategory("office");
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other.setBackgroundColor(Color.LTGRAY);
                question1.setText("How many did you lose?");
                question2.setText("What color is it");
                lostItem.setCategory("other");
            }
        });

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        itemPicture.setImageURI(uri);
                        saveUriPic = uri;
                    }
                });


        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        helpMeFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();


                 if(saveUriPic != null)
                     lostItem.setImage(saveUriPic);
                lostItem.setWhatLost(whatLostText.getText().toString());
                lostItem.setAnswer1(question1Answer.getText().toString());
                lostItem.setAnswer2(question2Answer.getText().toString());
                lostItem.setWhereLost(lostItemLocation.getText().toString());
                lostItem.setWhatLost(whatLostText.getText().toString());


                mDatabase.child("Users").child("LostItems").setValue(lostItem);

                Intent i = new Intent(UploadLost.this, ChoiceScreen.class);
                startActivity(i);



            }
        });



    }



}