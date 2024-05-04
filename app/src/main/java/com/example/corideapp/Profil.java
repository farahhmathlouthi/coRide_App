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
    Button log, owners, request, pi,aboutus;
    FirebaseAuth user;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);


        auth = FirebaseAuth.getInstance();
        log = findViewById(R.id.logout);
        owners = findViewById(R.id.choice4);
        pi = findViewById(R.id.choice1);
        aboutus = findViewById(R.id.choice2);
        user = auth;
        backButton = findViewById(R.id.back1);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Profil.this,Home.class);
                startActivity(intent);
                finish();            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), About_Us.class);
                startActivity(intent);
            }
        });
        owners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Owners.class);
                startActivity(intent);
            }
        });

        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), page_info_personnel.class);
                startActivity(intent);
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