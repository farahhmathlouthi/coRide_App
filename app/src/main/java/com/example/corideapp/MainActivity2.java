/*package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.Time;
import java.text.BreakIterator;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {
    //private boolean otpSent = false;
    private final String countryCode ="+216 ";
    EditText inputMobile;
    Button continueB;
    ProgressBar progressBar;
    private String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainasma1);
        progressBar = findViewById(R.id.progressBar1);
        inputMobile = findViewById(R.id.inputMobile);
        continueB = findViewById(R.id.continueB);

        FirebaseApp.initializeApp(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpSent) {
                    if (inputMobile.getText().toString().trim().isEmpty() || inputMobile.getText().toString().trim().length() != 8) {
                        Toast.makeText(MainActivity2.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otpEt);
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser userDetails = task.getResult().getUser();
                                    Toast.makeText(MainActivity2.this, "Verified", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity2.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else {
                    final String getMobile = inputMobile.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    continueB.setVisibility(View.INVISIBLE);

                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(countryCode + getMobile).setTimeout(60L, TimeUnit.SECONDS).setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                   Toast.makeText(MainActivity2.this,"OTP sent successfully",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                   Toast.makeText(MainActivity2.this,"Something went wrong"+ e.getMessage(),Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);
                            id = s;
                            otpSent = true;
                        }
                    }).build();

            PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });
    }
}*/