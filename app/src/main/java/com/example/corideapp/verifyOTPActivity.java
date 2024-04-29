package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class verifyOTPActivity extends AppCompatActivity {
    private ProgressBar progressBar2;
    private Button verifyB;
    protected EditText inputCode1, inputCode2, inputCode3 , inputCode4 , inputCode5, inputCode6, inputCode7, inputCode8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        TextView textEmail = findViewById(R.id.textEmail);
        textEmail.setText(String.format("to : ",getIntent().getStringExtra("mobile")));
        verifyB = findViewById(R.id.verifyB);

        progressBar2 = findViewById(R.id.progressBar2);
        TextView resendlabel = findViewById(R.id.textResendOTP);

        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);
        inputCode7 = findViewById(R.id.inputCode7);
        inputCode8 = findViewById(R.id.inputCode8);

        setupOTPInputs();



        ///resendlabel.setOnClickListener(new View.OnClickListener() {});
        verifyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputCode1.getText().toString().trim().isEmpty() && !inputCode2.getText().toString().trim().isEmpty() && !inputCode3.getText().toString().trim().isEmpty() && !inputCode4.getText().toString().trim().isEmpty() && !inputCode5.getText().toString().trim().isEmpty() && !inputCode6.getText().toString().trim().isEmpty() && !inputCode7.getText().toString().trim().isEmpty() && !inputCode8.getText().toString().trim().isEmpty()) {
                    String numberotp = inputCode1.getText().toString() +
                            inputCode2.getText().toString() +
                            inputCode3.getText().toString() +
                            inputCode4.getText().toString() +
                            inputCode5.getText().toString() +
                            inputCode6.getText().toString() +
                            inputCode7.getText().toString() +
                            inputCode8.getText().toString() ;

                    //if (getotpbackend!=null){
                    //progressBar2.setVisibility(View.VISIBLE);
                    // verifyB.setVisibility(View.INVISIBLE);
                    verifyOTP(numberotp);

                } else{
                    Toast.makeText(verifyOTPActivity.this, "Please Enter All Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifyOTP(String numberotp) {
        progressBar2.setVisibility(View.VISIBLE);

        String email = getIntent().getStringExtra("email"); // Get the email from Intent

        // Use EmailAuthProvider to sign in with email and password
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, numberotp)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar2.setVisibility(View.GONE);
                        verifyB.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            Toast.makeText(verifyOTPActivity.this, "OTP verified", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(verifyOTPActivity.this, Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(verifyOTPActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    String getotpbackend;
    private void setupOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode6.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode7.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        inputCode7.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode8.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
