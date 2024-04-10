package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainasma1);

        final EditText inputMobile = findViewById(R.id.inputMobile);
        Button continueB = findViewById(R.id.continueB);

        final ProgressBar progressBar = findViewById(R.id.progressBar1);
        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputMobile.getText().toString().trim().isEmpty() || inputMobile.getText().toString().trim().length() != 8 ) {
                    Toast.makeText(MainActivity2.this,"Enter Your Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                continueB.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(getApplicationContext(),verifyOTPActivity.class);
                intent.putExtra("mobile", inputMobile.getText().toString());
                startActivity(intent);
            }
        });
    }
}