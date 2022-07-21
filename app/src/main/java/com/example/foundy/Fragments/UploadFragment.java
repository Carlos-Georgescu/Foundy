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

import com.example.foundy.BuildConfig;
import com.example.foundy.FragmentChoiceScreen;
import com.example.foundy.R;
import com.example.foundy.Structures.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadFragment extends Fragment {

    DatePickerDialog.OnDateSetListener mSetListener;
    Item mItem;
    Boolean mIfFoundUpload = false;
    int  mNumOfImages = 0;
    double mItemLongitude;
    double mItemLatitude;
    EditText mWhatLostText;
    EditText mQuestion1Answer;
    EditText mQuestion2Answer;
    DatabaseReference mDatabase;
    EditText mLostItemLocation;
    Uri[] mSaveUriPic = new Uri[1];
    String mUserUid;
    ArrayList<String> mPotentialMatches;
    Button mElectronic;
    Button mJewlery;
    Button mClothing;
    Button mToys;
    EditText mSetDate;
    Button mOffice;
    Button mOther;
    TextView mQuestion1;
    TextView mQuestion2;
    Button mOpenMapButton;
    Button mTakePictureButton;
    ImageView mItemPicture;
    Button mHelpMeFind;
    TextView mTopText;
    View mRootView;
    TreeMap<Double,String> mMapOfScores = new TreeMap<Double,String>();
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users").child("FoundItems");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         mRootView = inflater.inflate(R.layout.activity_upload_lost, container, false);

        restoreFields(savedInstanceState);

        setUpFields();

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
                mTopText.setText("ADD NEW FOUND ITEM");
                mHelpMeFind.setText("LETS FIND IT");
            }
        }
        else {
            mIfFoundUpload = false;
        }


        mSetDate.setOnClickListener(new View.OnClickListener() {
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
                String date = dayOfMonth + " / " + month + " / " + year;
                mSetDate.setText(date);
            }
        };


        mOpenMapButton.setOnClickListener(new View.OnClickListener() {
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
            mLostItemLocation.setText(userLostItemLocation);

            mItemLatitude = getArguments().getDouble("latitude");
            mItemLongitude = getArguments().getDouble("longitude");

        }

        categoryButtonSelection();

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        mItemPicture.setImageURI(uri);
                        mSaveUriPic[0] = uri;
                    }
                });


        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });



        mHelpMeFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setUpDatabaseAndFields();

                if(mIfFoundUpload == false) {

                    mDatabase.child("Users").child(mUserUid).child("LostItems").push().setValue(mItem);
                }
                else {
                    mDatabase.child("Users").child("FoundItems").push().setValue(mItem);
                }
                mNumOfImages++;

                Intent i = new Intent(getContext(), FragmentChoiceScreen.class);
                startActivity(i);

                itemMatchingAlgorithm();

            }
        });

        return mRootView;
    }

    private void setUpFields() {
        mPotentialMatches = new ArrayList<>();
        mSetDate = mRootView.findViewById(R.id.selectDateText);
        mQuestion1Answer = mRootView.findViewById(R.id.question1Answer);
        mQuestion2Answer = mRootView.findViewById(R.id.question2Answer);
        mItemPicture = mRootView.findViewById(R.id.itemPicture);
        mOpenMapButton = mRootView.findViewById(R.id.openMapButton);
        mLostItemLocation = mRootView.findViewById(R.id.lostItemLocation);
        mElectronic = mRootView.findViewById(R.id.electronic);
        mJewlery = mRootView.findViewById(R.id.jewlery);
        mClothing = mRootView.findViewById(R.id.clothing);
        mToys = mRootView.findViewById(R.id.toys);
        mOffice = mRootView.findViewById(R.id.office);
        mOther = mRootView.findViewById(R.id.other);
        mQuestion1 = mRootView.findViewById(R.id.question1);
        mQuestion2 = mRootView.findViewById(R.id.question2);
        mTakePictureButton = mRootView.findViewById(R.id.takePictureButton);
        mHelpMeFind = mRootView.findViewById(R.id.helpMeFind);
        mWhatLostText = mRootView.findViewById(R.id.whatLostText);
        mTopText = mRootView.findViewById(R.id.topText);
    }

    private void categoryButtonSelection() {
        mElectronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mElectronic.setBackgroundColor(Color.LTGRAY);
                mQuestion1.setText("What kind of device is it?");
                mQuestion2.setText("What model is it?");
                mItem.setCategory("electronic");
            }
        });

        mJewlery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJewlery.setBackgroundColor(Color.LTGRAY);
                mQuestion1.setText("How color is it?");
                mQuestion2.setText("How much is it worth?");
                mItem.setCategory("jewlery");
            }
        });

        mClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClothing.setBackgroundColor(Color.LTGRAY);
                mQuestion1.setText("What brand is it?");
                mQuestion2.setText("Any standout qualities about it?");
                mItem.setCategory("clothing");
            }
        });

        mToys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToys.setBackgroundColor(Color.LTGRAY);
                mQuestion1.setText("What brand is it?");
                mQuestion2.setText("What color is it?");
                mItem.setCategory("toys");
            }
        });

        mOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOffice.setBackgroundColor(Color.LTGRAY);
                mQuestion1.setText("How many did you lose?");
                mQuestion2.setText("Where are they from?");
                mItem.setCategory("office");
            }
        });

        mOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOther.setBackgroundColor(Color.LTGRAY);
                mQuestion1.setText("How many did you lose?");
                mQuestion2.setText("What color is it");
                mItem.setCategory("other");
            }
        });
    }

    private void setUpDatabaseAndFields() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
         mUserUid = user.getUid();

        mItem.setImageLocationString(uploadImage(mSaveUriPic[0]));
        mItem.setWhatLost(mWhatLostText.getText().toString());
        mItem.setAnswer1(mQuestion1Answer.getText().toString());
        mItem.setAnswer2(mQuestion2Answer.getText().toString());
        mItem.setWhereLost(mLostItemLocation.getText().toString());
        mItem.setWhatLost(mWhatLostText.getText().toString());
        mItem.setDate(mSetDate.getText().toString());
        mItem.setLatitude(mItemLatitude);
        mItem.setUserID(mUserUid);
        mItem.setMatched(false);
        mItem.setLongitude(mItemLongitude);
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



        mRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        try {
                            collectAllUsers((Map<String, Object>) dataSnapshot.getValue());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }

                    private void collectAllUsers(Map<String, Object> users) throws JSONException {

                        //iterate through each user, ignoring their UID
                        if (users != null)
                            for (Map.Entry<String, Object> entry : users.entrySet()) {

                                Map singleItem = (Map) entry.getValue();

                                double newItemLongitude = (double) singleItem.get("longitude");
                                double newItemLatitude = (double) singleItem.get("latitude");



                                double distanceDifference = calculateDistanceBetweenPoints(mItem.getLatitude(), newItemLatitude, mItem.getLongitude(), newItemLongitude);

                                Log.i("UploadFragment", "Distance Difference: " + distanceDifference);

                                // only adds to potentialMatches array if the objects are less than 500m apart and of the same category
                                if(distanceDifference < 500 && mItem.getCategory().equals((String)singleItem.get("category")));
                                {
                                    String uniqueID = (String) singleItem.get("imageLocationString");
                                    String answer1 = (String) singleItem.get("answer1");
                                    String answer2 = (String) singleItem.get("answer2");
                                    String date = (String) singleItem.get("date");

                                    String str[] = date.split(" / ");

                                    int day = Integer.parseInt(str[0]);
                                    int month = Integer.parseInt(str[1]);
                                    int year = Integer.parseInt(str[2]);



                                    mPotentialMatches.add(uniqueID);
                                    double score = calculateOverallScore(answer1, answer2, day, month, year);
                                }



                            };
                        Log.i("UploadFragment", "Long: " + mPotentialMatches.toString());
                        }
                    }
                );
    }

    private double calculateOverallScore(String answer1, String answer2, int foundYear, int foundMonth, int foundDay) {
        double textSimily1 = findTextSimiliary(mQuestion1Answer.getText().toString(), answer1);
        double textSimily2 = findTextSimiliary(mQuestion2Answer.getText().toString(), answer2);

        double finalTextSimilyScore = (textSimily1 + textSimily2) / 2;

        String date = mSetDate.getText().toString();
        String str[] = date.split(" / ");

        int lostDay = Integer.parseInt(str[0]);
        int lostMonth = Integer.parseInt(str[1]);
        int lostYear = Integer.parseInt(str[2]);

        double dateScore =  dateSimilarityAlgorithm(lostYear, foundYear,lostMonth, foundMonth, lostDay, foundDay);

        return finalTextSimilyScore * 0.4 + dateScore * 0.6;
    }

    // returns the amount of meters between the two points
    public double calculateDistanceBetweenPoints(double lat1, double lat2, double long1, double long2){
            double earthRadius = 6371200; //meters
            double differentLat = Math.toRadians(lat2-lat1);
            double differenceLong = Math.toRadians(long2-long1);
            double a = Math.sin(differentLat/2) * Math.sin(differentLat/2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                            Math.sin(differenceLong/2) * Math.sin(differenceLong/2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double dist = (double) (earthRadius * c);

            return dist;
    }

    public double findTextSimiliary(String stringToCompare1, String stringToCompare2) throws JSONException, IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("text1" ,stringToCompare1)
                .add("text2", stringToCompare2)
                .build();

        //i hid the API key
        String api_key = BuildConfig.TEXT_KEY;


        Request request = new Request.Builder()
                .url("https://twinword-text-similarity-v1.p.rapidapi.com/similarity/")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", api_key)
                .addHeader("X-RapidAPI-Host", "twinword-text-similarity-v1.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            double score = 0;
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String responseBody = response.body().string();
                Log.i("UploadFragment", "Response body: "+responseBody);

                JSONObject object = null;
                try {
                    object = new JSONObject(responseBody);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                     score = Double.parseDouble(object.get("similarity").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return 0;
    }

    // returns a value between 0 - 1, 1 being very similiar, 0 being not similar at all, year 1 is the lost date
    public double dateSimilarityAlgorithm(int year1, int year2, int day1, int day2, int month1, int month2)
    {
        if(year1 - year2 > 1)
            return 0;


        int counter = 0;

        while(day2 < day1)
        {
            if(numInMonth(month2,  year2) == day2)
            {
                day2 = 1;
                if(month2 == 12)
                    month2 = 1;
                else
                    month2++;
            }
            else
                day2++;

            counter++;
        }

        if(month2 != month1)
        {
           counter += numInMonth(month2, year2);
        }

        if(counter > 20)
            return 0.0;
        else
            return counter / 20.0;

    }

    public int numInMonth(int month, int year)
    {
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            return 31;
        if(month == 2 && isLeapYear(year))
            return 29;
        if(month == 2 && isLeapYear(year) == false)
            return 28;

        return  30;
    }

    public boolean isLeapYear(int year)
    {
        return (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0));
    }
}