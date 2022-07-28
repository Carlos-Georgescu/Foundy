package com.example.foundy.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foundy.Adapters.LostItemAdapter;
import com.example.foundy.Adapters.ProfileMatchedAdapter;
import com.example.foundy.MeetupScreen;
import com.example.foundy.R;
import com.example.foundy.RecyclerViewInterface;
import com.example.foundy.Structures.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoundMatched#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoundMatched extends Fragment implements RecyclerViewInterface {

    private RecyclerView mRvPosts;
    private LostItemAdapter mAdapter;
    private List<Item> itemList;
    private final List<Uri> lostItemImages = new ArrayList<>();

    public FoundMatched() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_found_matched, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvPosts = view.findViewById(R.id.rvFoundItems);

        RecyclerView mPosts = view.findViewById(R.id.rvFoundItems);

        // Inflate the layout for this fragment

        itemList = new ArrayList<>();
        queryPosts();
        mAdapter = new LostItemAdapter(getContext(), itemList, lostItemImages, this);
        mRvPosts.setAdapter(mAdapter);
        mRvPosts.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    private void queryPosts() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        StorageReference listRef = FirebaseStorage.getInstance().getReference("lostFiles/" + userUid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userUid).child("LostItems");

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectAllUsers((Map<String, Object>) dataSnapshot.getValue());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }

                    private void collectAllUsers(Map<String, Object> users) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userUid = user.getUid();

                        //iterate through each user, ignoring their UID
                        if (users != null)
                            for (Map.Entry<String, Object> entry : users.entrySet()) {



                                //Get user map
                                Map singleUser = (Map) entry.getValue();

                                String dataUserID = (String) singleUser.get("userID");
                                Boolean isit = (Boolean)singleUser.get("matched");
                                //Get phone field and append to list

                                Boolean isF = (Boolean) ((Boolean)singleUser.get("isFound"));

                                Log.i("FoundMatched", "Matchedd: "+isit);
                                Log.i("FoundMatched", "Matchhh: "+isF);


                                if(((Boolean)singleUser.get("matched")) == true  && ((Boolean)singleUser.get("isFound")) == true) {
                                    Item newItem = new Item();
                                    newItem.setWhatLost((String) singleUser.get("whatLost"));
                                    newItem.setWhereLost((String) singleUser.get("whereLost"));
                                    newItem.setDate((String) singleUser.get("category"));

                                    String imageLocation = (String) singleUser.get("imageLocationString");
                                    Log.i("LostMatched", "image location " + imageLocation + "Image what " + newItem.getWhatLost());

                                    itemList.add(newItem);
                                    lostItemImages.add(null);
                                    mAdapter.notifyDataSetChanged();

                                    int index = itemList.size() - 1;

                                    Log.i("HomeFragment", "Location of list" + listRef.child("/" + imageLocation));
                                    listRef.child("/" + imageLocation).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            lostItemImages.set(index, uri);
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                    }
                });
    }

    @Override
    public void OnItemClick(int position) {

        Intent i = new Intent(getActivity(), MeetupScreen.class);
        startActivity(i);
    }
}