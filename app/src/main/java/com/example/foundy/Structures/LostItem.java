package com.example.foundy.Structures;

import android.net.Uri;

import java.net.URI;

public class LostItem {
    private String whatLost;
    private String whereLost;
    private String date;
    private String category;
    private String answer1;
    private String answer2;
    private Uri image;

    public LostItem(){

    }

    public LostItem(String whatLost, String whereLost, String date, String category, String answer1, String answer2, Uri image) {
        this.whatLost = whatLost;
        this.whereLost = whereLost;
        this.date = date;
        this.category = category;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.image = image;
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

    public Uri getImage() {
        return image;
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

    public void setImage(Uri image) {
        this.image = image;
    }
}
