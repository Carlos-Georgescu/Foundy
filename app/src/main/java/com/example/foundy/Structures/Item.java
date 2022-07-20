package com.example.foundy.Structures;

import android.net.Uri;

import com.example.foundy.R;

import java.net.URI;


public class LostItem {
    private String whatLost;
    private String whereLost;
    private String date;
    private String category;
    private String answer1;
    private String answer2;
    private String imageLocationString;

    public LostItem(){

    }

    public LostItem(String whatLost, String whereLost, String date, String category, String answer1, String answer2, String imageLocationString) {
        this.whatLost = whatLost;
        this.whereLost = whereLost;
        this.date = date;
        this.category = category;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.imageLocationString = imageLocationString;
    }

    public String getWhatLost() {
        return whatLost;
    }

    public String getWhereLost() {
        return whereLost;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }


    public String getImageLocationString() {
        return imageLocationString;
    }

    public void setImageLocationString(String imageLocationString) {
        this.imageLocationString = imageLocationString;
    }

    public void setWhatLost(String whatLost) {
        this.whatLost = whatLost;
    }

    public void setWhereLost(String whereLost) {
        this.whereLost = whereLost;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

}
