package com.example.corideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity3_signin extends AppCompatActivity {

    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3_signin);
    passwordEditText = findViewById(R.id.editTextTextPassword) ;


    }
}