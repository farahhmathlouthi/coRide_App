package com.example.corideapp;

public class RideRequest {
    private String arrival;
    private String departure;
    private String date;
    private String time;
    private String fullName;
    private String phone;

    // Default constructor (required by Firebase)
    public RideRequest() {
    }

    // Constructor with all fields
    public RideRequest(String arrival, String departure, String date, String time, String fullName, String phone) {
        this.arrival = arrival;
        this.departure = departure;
        this.date = date;
        this.time = time;
        this.fullName = fullName;
        this.phone = phone;
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
    public String getName() {
        return fullName;
    }

    public void setName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Repeat for other fields...
}

