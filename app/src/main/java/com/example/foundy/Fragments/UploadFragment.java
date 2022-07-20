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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foundy.FragmentChoiceScreen;
import com.example.foundy.R;
import com.example.foundy.Structures.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

public class UploadFragment extends Fragment {

    DatePickerDialog.OnDateSetListener mSetListener;
    Item mItem;
    Boolean mIfFoundUpload = false;
    int  mNumOfImages = 0;
    double itemLongitude;
    double itemLatitude;

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
        TextView topText;

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
        topText = rootView.findViewById(R.id.topText);

        mItem = new Item();


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        if(getArguments() != null && getArguments().getString("type") != null)
        {
            if (getArguments().getString("type").equals("found"))
            {
                mIfFoundUpload = true;
                topText.setText("ADD NEW FOUND ITEM");
                helpMeFind.setText("LETS FIND IT");
            }
        }
        else {
            mIfFoundUpload = false;
        }


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
                Bundle bundle = new Bundle();
                if(mIfFoundUpload == true)
                    bundle.putString("type", "found");
                map.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, map).commit();

            }
        });


        if (getArguments() != null) {
            String userLostItemLocation = getArguments().getString("location");
            lostItemLocation.setText(userLostItemLocation);

            itemLatitude = getArguments().getDouble("latitude");
            itemLongitude = getArguments().getDouble("longitude");

        }

        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electronic.setBackgroundColor(Color.LTGRAY);
                question1.setText("What kind of device is it?");
                question2.setText("What model is it?");
                mItem.setCategory("electronic");
            }
        });

        jewlery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jewlery.setBackgroundColor(Color.LTGRAY);
                question1.setText("How color is it?");
                question2.setText("How much is it worth?");
                mItem.setCategory("jewlery");
            }
        });

        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clothing.setBackgroundColor(Color.LTGRAY);
                question1.setText("What brand is it?");
                question2.setText("Any standout qualities about it?");
                mItem.setCategory("clothing");
            }
        });

        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toys.setBackgroundColor(Color.LTGRAY);
                question1.setText("What brand is it?");
                question2.setText("What color is it?");
                mItem.setCategory("toys");
            }
        });

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                office.setBackgroundColor(Color.LTGRAY);
                question1.setText("How many did you lose?");
                question2.setText("Where are they from?");
                mItem.setCategory("office");
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other.setBackgroundColor(Color.LTGRAY);
                question1.setText("How many did you lose?");
                question2.setText("What color is it");
                mItem.setCategory("other");
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

                mItem.setImageLocationString(uploadImage(saveUriPic[0]));
                mItem.setWhatLost(whatLostText.getText().toString());
                mItem.setAnswer1(question1Answer.getText().toString());
                mItem.setAnswer2(question2Answer.getText().toString());
                mItem.setWhereLost(lostItemLocation.getText().toString());
                mItem.setWhatLost(whatLostText.getText().toString());
                mItem.setLatitude(itemLatitude);
                mItem.setLongitude(itemLongitude);

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                String userUid = user.getUid();

                if(mIfFoundUpload == false) {

                    mDatabase.child("Users").child(userUid).child("LostItems").push().setValue(mItem);
                }
                else {
                    mDatabase.child("Users").child(userUid).child("FoundItems").push().setValue(mItem);
                }
                mNumOfImages++;


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

    }

    public String uploadImage(Uri image){
        // Create a Cloud Storage reference from the app
        FirebaseStorage storage = FirebaseStorage.getInstance();


        StorageReference storageRef = storage.getReference();
        StorageReference reference;
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();
        String randomgUI = "";


        Log.i("UploadFragment" ,"Value of number of images: "+mNumOfImages);


        if(mIfFoundUpload == true) {
             randomgUI = UUID.randomUUID().toString();
            reference = storageRef.child("foundFiles/" + userUid + "/" + randomgUI);
        }
        else {
             randomgUI = UUID.randomUUID().toString();
            reference = storageRef.child("lostFiles/" + userUid + "/" + randomgUI);
        }


        reference.putFile(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.e("UploadFragment","Error uploading photo");
                }
            }
        });
        return randomgUI;
    }

    public void itemMatchingAlgorithm(){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}