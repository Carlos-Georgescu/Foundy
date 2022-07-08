package com.example.foundy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import pl.droidsonroids.gif.GifImageView;

public class SlideLayoutAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideLayoutAdapter(Context context){
        this.context = context;
    }

    public String[] slide_images = {
            "res/raw/slide1photo.gif",
            "res/raw/logimage.mp4",
            "res/raw/logimage.mp4"
    };

    public String[] slide_headings = {
            "Welcome to Foundy",
            "Trust System",
            "Lost Items"
        };

    public String[] slide_descs = {
            "Foundy is a Lost and Found App that helps you find your lost items, and finds a place to meet with the founder :) ",
            "Please only claim items that are yours, as we run on a huge trust system",
            "Remember to post any lost items you found, and to keep those lost items in a safe place before someone contacts you!"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_slide_layout, container, false);

        VideoView slideImageView = (VideoView) view.findViewById(R.id.slideVideo);
        TextView slideHeading = (TextView) view.findViewById(R.id.slideHeading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slideDescription);

        slideImageView.setVideoPath(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}