package com.example.foundy.Structures;

public class Meetup {
    private String city;
    private String state;
    private String locationName;
    private int hour;
    private int minute;
    private String estimatedTime;
    private String lostUID;
    private String foundUID;

    public Meetup() {
    }

    public String getLostUID() {
        return lostUID;
    }

    public void setLostUID(String lostUID) {
        this.lostUID = lostUID;
    }

    public String getFoundUID() {
        return foundUID;
    }

    public void setFoundUID(String foundUID) {
        this.foundUID = foundUID;
    }

    public Meetup(String city, String state, String locationName, int hour, int minute, String estimatedTime, String lostUID, String foundUID) {
        this.city = city;
        this.state = state;
        this.locationName = locationName;
        this.hour = hour;
        this.minute = minute;
        this.estimatedTime = estimatedTime;
        this.lostUID = lostUID;
        this.foundUID = foundUID;
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
