package com.example.foundy.Structures;

public class Meetup {
    public String city;
    public String state;
    public String locationName;
    public int hour;
    public int minute;
    public String estimatedTime;

    public Meetup(String city, String state, String locationName, int hour, int minute, String estimatedTime) {
        this.city = city;
        this.state = state;
        this.locationName = locationName;
        this.hour = hour;
        this.minute = minute;
        this.estimatedTime = estimatedTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
