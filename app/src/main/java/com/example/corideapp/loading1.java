package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class loading1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading1);

        // Delay the start of the Home activity by a certain duration (e.g., 2 seconds)
        View decorView = getWindow().getDecorView();
        decorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the Home activity after the delay
                Intent intent = new Intent(loading1.this, Home.class);
                startActivity(intent);

                // Finish the loading1 activity to remove it from the back stack
                finish();
            }
        }, 2000); // 2000 milliseconds = 2 seconds (adjust as needed)
    }
}

