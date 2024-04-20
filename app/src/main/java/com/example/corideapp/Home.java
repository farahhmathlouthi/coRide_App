package com.example.corideapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView cardView1 = findViewById(R.id.rideGiverB);
        CardView cardView2 = findViewById(R.id.rideTakerB);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),rideGiver.class);
                //intent.putExtra("mobile", inputMobile.getText().toString());
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),rideTaker.class);
                //intent.putExtra("mobile", inputMobile.getText().toString());
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.bottom_home) {
                    return true;
                } else if (item.getItemId() == R.id.bottom_planning) {
                    startActivity(new Intent(getApplicationContext(), Planning.class));
                    overridePendingTransition(R.animator.slide_in_right, R.animator.slide_to_left);
                    finish();
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
