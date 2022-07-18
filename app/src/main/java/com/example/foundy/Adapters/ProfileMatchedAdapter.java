package com.example.foundy.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foundy.Fragments.FoundMatched;
import com.example.foundy.Fragments.LostMatched;

public class ProfileMatchedAdapter extends FragmentStateAdapter {

    public ProfileMatchedAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        switch(position){
            case 0:
                return new FoundMatched();
            case 1:
                return new LostMatched();
        }
        return new FoundMatched();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
