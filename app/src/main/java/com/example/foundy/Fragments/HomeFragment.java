package com.example.foundy.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foundy.Adapters.LostItemAdapter;
import com.example.foundy.R;
import com.example.foundy.Structures.LostItem;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRvPosts;
    private LostItemAdapter mAdapter;
    private List<LostItem> lostItemList;
    private final List<Uri> lostItemImages = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvPosts = view.findViewById(R.id.rvPosts);

        lostItemList = new ArrayList<>();
        // collectAllImage();
        queryPosts();
        mAdapter = new LostItemAdapter(getContext(), lostItemList, lostItemImages);
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

                        //iterate through each user, ignoring their UID
                        if (users != null)
                            for (Map.Entry<String, Object> entry : users.entrySet()) {

                                //Get user map
                                Map singleUser = (Map) entry.getValue();
                                //Get phone field and append to list

                                LostItem newItem = new LostItem();
                                newItem.setWhatLost((String) singleUser.get("whatLost"));
                                newItem.setWhereLost((String) singleUser.get("whereLost"));
                                newItem.setDate((String) singleUser.get("category"));

                                String imageLocation = (String) singleUser.get("imageLocationString");
                                Log.i("HomeFramgent", "image location " + imageLocation + "Image what " + newItem.getWhatLost());
                                mAdapter.notifyDataSetChanged();

                                Log.i("HomeFragment", "Location of list" + listRef.child("/" + imageLocation));
                                listRef.child("/" + imageLocation).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        lostItemImages.add(uri);
                                        //mAdapter.notifyDataSetChanged();
                                        lostItemList.add(newItem);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                            }

                    }


                });
    }

}