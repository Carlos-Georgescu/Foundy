package com.example.foundy.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foundy.FragmentChoiceScreen;
import com.example.foundy.R;
import com.example.foundy.Structures.LostItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

public class UploadFragment extends Fragment {

    DatePickerDialog.OnDateSetListener mSetListener;
//    FragmentTransaction mFragmentTrasaction = getFragmentManager().beginTransaction();
    FragmentManager manager = getFragmentManager();
    //FragmentTransaction transaction = manager.beginTransaction();
    LostItem mLostItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_upload_lost, container, false);

        restoreFields(savedInstanceState);


        Button openMapButton;
        EditText lostItemLocation;
        Button electronic;
        Button jewlery;
        Button clothing;
        Button toys;
        EditText setDate;
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
        Uri[] saveUriPic = new Uri[1];

        setDate = rootView.findViewById(R.id.selectDateText);
        question1Answer = rootView.findViewById(R.id.question1Answer);
        question2Answer = rootView.findViewById(R.id.question2Answer);
        itemPicture = rootView.findViewById(R.id.itemPicture);
        openMapButton = rootView.findViewById(R.id.openMapButton);
        lostItemLocation = rootView.findViewById(R.id.lostItemLocation);
        electronic = rootView.findViewById(R.id.electronic);
        jewlery = rootView.findViewById(R.id.jewlery);
        clothing = rootView.findViewById(R.id.clothing);
        toys = rootView.findViewById(R.id.toys);
        office = rootView.findViewById(R.id.office);
        other = rootView.findViewById(R.id.other);
        question1 = rootView.findViewById(R.id.question1);
        question2 = rootView.findViewById(R.id.question2);
        takePictureButton = rootView.findViewById(R.id.takePictureButton);
        helpMeFind = rootView.findViewById(R.id.helpMeFind);
        whatLostText = rootView.findViewById(R.id.whatLostText);
        mLostItem = new LostItem();


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mSetListener = new DatePickerDialog.OnDateSetListener() {
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
                Fragment map = new MapsFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, map).commit();

            }
        });


        if (getArguments() != null) {
            String userLostItemLocation = getArguments().getString("location");
            System.out.println("UserLostItem "+userLostItemLocation);
            lostItemLocation.setText(userLostItemLocation);
        }

        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electronic.setBackgroundColor(Color.LTGRAY);
                question1.setText("What kind of device is it?");
                question2.setText("What model is it?");
                mLostItem.setCategory("electronic");
            }
        });

        jewlery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jewlery.setBackgroundColor(Color.LTGRAY);
                question1.setText("How color is it?");
                question2.setText("How much is it worth?");
                mLostItem.setCategory("jewlery");
            }
        });

        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clothing.setBackgroundColor(Color.LTGRAY);
                question1.setText("What brand is it?");
                question2.setText("Any standout qualities about it?");
                mLostItem.setCategory("clothing");
            }
        });

        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toys.setBackgroundColor(Color.LTGRAY);
                question1.setText("What brand is it?");
                question2.setText("What color is it?");
                mLostItem.setCategory("toys");
            }
        });

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                office.setBackgroundColor(Color.LTGRAY);
                question1.setText("How many did you lose?");
                question2.setText("Where are they from?");
                mLostItem.setCategory("office");
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other.setBackgroundColor(Color.LTGRAY);
                question1.setText("How many did you lose?");
                question2.setText("What color is it");
                mLostItem.setCategory("other");
            }
        });

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        itemPicture.setImageURI(uri);
                        saveUriPic[0] = uri;
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



                mLostItem.setWhatLost(whatLostText.getText().toString());
                mLostItem.setAnswer1(question1Answer.getText().toString());
                mLostItem.setAnswer2(question2Answer.getText().toString());
                mLostItem.setWhereLost(lostItemLocation.getText().toString());
                mLostItem.setWhatLost(whatLostText.getText().toString());

                mDatabase.child("Users").child("LostItems").push().setValue(mLostItem);
                uploadImage(saveUriPic[0]);

                Intent i = new Intent(getContext(), FragmentChoiceScreen.class);
                startActivity(i);



            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
    }

    public void restoreFields(Bundle savedInstanceState){
        if(savedInstanceState != null)
        {
            mLostItem.setWhatLost(savedInstanceState.getString("what"));
        }
    }

    public void uploadImage(Uri image){
        // Create a Cloud Storage reference from the app
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("files/uid");

        storageRef.putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("UploadFragment","Error uploading photo");
                }
            }
        });
    }
}