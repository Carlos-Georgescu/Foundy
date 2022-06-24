package com.example.foundy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SlideLayoutAdapter sliderAsapter;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderAsapter = new SlideLayoutAdapter(this);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mSlideViewPager.setAdapter(sliderAsapter);

        addDotsIndicator();

    }


}