package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class welcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button signin = findViewById(R.id.signin);
        Button signup = findViewById(R.id.signup);

        final ProgressBar progressBar = findViewById(R.id.progressBar1);
        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity2.this,"Enter Your Email Adress !",Toast.LENGTH_SHORT).show();
                } else {
                    sendVerificationEmail(email);
                    continueB.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }

                Intent intent = new Intent(getApplicationContext(),verifyOTPActivity.class);
                intent.putExtra("mobile", inputEmail.getText().toString());
                startActivity(intent);
            }
        });
    }
}