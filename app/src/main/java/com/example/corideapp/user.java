package com.example.corideapp;

public class user {

    public String username;
    public String email;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user(String username, String email) {
        this.username = username;
        this.email = email;
    }

}