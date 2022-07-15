package com.example.foundy.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foundy.Adapters.LostItemAdapter;
import com.example.foundy.R;
import com.example.foundy.Structures.LostItem;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRvPosts;
    private LostItemAdapter mAdapter;
    private List<LostItem> lostItemList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvPosts = view.findViewById(R.id.rvPosts);

        lostItemList = new ArrayList<>();
        mAdapter = new LostItemAdapter(getContext(), lostItemList);

        mRvPosts.setAdapter(mAdapter);
        mRvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}