package com.example.corideapp;

public class RideRequest {
    private String arrival;
    private String departure;
    private String date;
    private String time;
    private String userName;
    private String userPhone;

    // Default constructor (required by Firebase)
    public RideRequest() {
    }

    // Constructor with all fields
    public RideRequest(String arrival, String departure, String date, String time, String userName, String userPhone) {
        this.arrival = arrival;
        this.departure = departure;
        this.date = date;
        this.time = time;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    // Getters and setters (required for Firebase)
    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name; // Assign the parameter to the userName field
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String phone) {
        this.userPhone = phone; // Assign the parameter to the userPhone field
    }
}

