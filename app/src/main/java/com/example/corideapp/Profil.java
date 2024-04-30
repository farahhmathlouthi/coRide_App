package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Profil extends AppCompatActivity {

    FirebaseAuth auth;
    Button log;
    FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);


        auth = FirebaseAuth.getInstance();
        log = findViewById(R.id.logout);
        user = auth;
        ImageView backButton = findViewById(R.id.back1);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Navigate back to the previous activity
            }
        });


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