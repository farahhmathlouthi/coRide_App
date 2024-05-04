package com.example.corideapp;

public class RideRequest2 {
    private String arrival2;
    private String departure2;
    private String date2;
    private String time2;
    private String fullName2;
    private String phone2;
    private String places;

    // Default constructor (required by Firebase)
    public RideRequest2() {
    }

    // Constructor with all fields
    public RideRequest2(String arrival2, String departure2, String date2, String time2, String fullName2, String phone2, String places) {
        this.fullName2 = fullName2;
        this.phone2 = phone2;
        this.arrival2 = arrival2;
        this.departure2 = departure2;
        this.date2 = date2;
        this.time2 = time2;
        this.places = places;
    }

    // Getters and setters (required for Firebase)
    public String getArrival() {
        return arrival2;
    }

    public void setArrival(String arrival2) {
        this.arrival2 = arrival2;
    }

    public String getDeparture() {
        return departure2;
    }

    public void setDeparture(String departure2) {
        this.departure2 = departure2;
    }
    public String getDate() {
        return date2;
    }

    public void setDate(String date2) {
        this.date2 = date2;
    }
    public String getTime() {
        return time2;
    }

    public void setTime(String time2) {
        this.time2 = time2;
    }
    public String getUserName() {
        return fullName2;
    }

    public void setUserName(String fullName2) {
        this.fullName2 = fullName2;
    }
    public String getUserPhone() {
        return phone2;
    }

    public void setUserPhone(String phone2) {
        this.phone2 = phone2;
    }

    public String getPlaces () {
        return places;
    }

    public void setPlace (String places) {
        this.places = places;
    }

    // Repeat for other fields...
}
