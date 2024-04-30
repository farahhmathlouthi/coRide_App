package com.example.corideapp;

public class user {

    public String username;
    public String nameU;
    public String email;
    public String address;
    public String phone;


    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public user(String phone, String address ,String nameU){
        this.address = address;
        this.phone = phone;
        this.nameU = nameU;
    }
    public user(String username, String email ) {
        this.username = username;
        this.email = email;

    }

}