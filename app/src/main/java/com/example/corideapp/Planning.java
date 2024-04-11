package com.example.corideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Planning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_planning);


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.bottom_home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(R.animator.slide_in_right, R.animator.slide_to_left);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.bottom_planning) {
                    return true;
                } else if (item.getItemId() == R.id.bottom_favoris) {
                    startActivity(new Intent(getApplicationContext(), Favoris.class));
                    overridePendingTransition(R.animator.slide_in_right, R.animator.slide_to_left);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.bottom_profile) {
                    startActivity(new Intent(getApplicationContext(), Profil.class));
                    overridePendingTransition(R.animator.slide_in_right, R.animator.slide_to_left);
                    finish();
                    return true;
                }
                return false;
            }
        });


    }
}