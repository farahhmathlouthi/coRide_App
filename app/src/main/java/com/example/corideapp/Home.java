package com.example.corideapp;
import com.example.corideapp.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_planning :
                    startActivity(new Intent(getApplicationContext(),Planning.class));
                    overridePendingTransition(R.animator.slide_in_right,R.animator.slide_to_left);
                    finish();
                    return true;
                case R.id.bottom_favoris :
                    startActivity(new Intent(getApplicationContext(),Favoris.class));
                    overridePendingTransition(R.animator.slide_in_right,R.animator.slide_to_left);
                    finish();
                    return true;
                case R.id.bottom_profile :
                    startActivity(new Intent(getApplicationContext(),Profil.class));
                    overridePendingTransition(R.animator.slide_in_right,R.animator.slide_to_left);
                    finish();
                    return true;
            }
            return false;
        });


    }
}