package com.example.foundy.Structures;

import java.net.URI;

public class LostItem {
    public String whatLost;
    public String whereLost;
    public String date;
    public String category;
    public String answer1;
    public String answer2;
    public URI image;

    public LostItem(String whatLost, String whereLost, String date, String category, String answer1, String answer2, URI image) {
        this.whatLost = whatLost;
        this.whereLost = whereLost;
        this.date = date;
        this.category = category;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.image = image;
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

    public void setImage(URI image) {
        this.image = image;
    }
}
