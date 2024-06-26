package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class requests extends AppCompatActivity {

    private EditText editTextText;
    private Button button9;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requests);

        // Delay the start of the Home activity by a certain duration (e.g., 2 seconds)
        View decorView = getWindow().getDecorView();
        decorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the Home activity after the delay
                Intent intent = new Intent(requests.this, Home.class);
                startActivity(intent);

                // Finish the loading1 activity to remove it from the back stack
                finish();
            }
        }, 1000); // 2000 milliseconds = 2 seconds (adjust as needed)



    }

}