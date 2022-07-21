package com.example.foundy.Structures;

import android.net.Uri;

import com.example.foundy.R;

import java.net.URI;


public class Item {
    private String whatLost;
    private String whereLost;
    private String date;
    private String category;
    private String answer1;
    private String answer2;
    private String imageLocationString;
    private Boolean isMatched;
    private double latitude;
    private double longitude;
    private String userID;

    public Item(){

    }

    public Item(String whatLost, String whereLost, String date, String category, String answer1, String answer2, String imageLocationString, double latitude, double longitude, String userID, Boolean isMatched) {
        this.whatLost = whatLost;
        this.whereLost = whereLost;
        this.date = date;
        this.category = category;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.imageLocationString = imageLocationString;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = userID;
        this.isMatched = isMatched;
    }

    public Boolean getMatched() {
        return isMatched;
    }

    public void setMatched(Boolean matched) {
        isMatched = matched;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
