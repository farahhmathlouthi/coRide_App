package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class settingsActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button log;
    FirebaseAuth user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        auth = FirebaseAuth.getInstance();
        log = findViewById(R.id.logout);
        user = auth;


        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), signInActivity.class);
            startActivity(intent);
            finish();
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), signInActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}