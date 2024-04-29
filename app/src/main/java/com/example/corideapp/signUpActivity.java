package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpActivity extends AppCompatActivity {


    EditText editTextEmail , editTextPassword , editTextName;
    Button signUpB;
    FirebaseAuth mAuth;
    ProgressBar SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        signUpB = findViewById(R.id.signUpButton);
        editTextName = findViewById(R.id.inputN);
        editTextEmail = findViewById(R.id.inputE2);
        editTextPassword = findViewById(R.id.inputP2);
        SP = findViewById(R.id.progressBarSignUp);
        signUpB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SP.setVisibility(View.VISIBLE);
                    String email, password;
                    email = String.valueOf(editTextEmail.getText());
                    password = String.valueOf(editTextPassword.getText());

                    if (TextUtils.isEmpty(email)) {

                        Toast.makeText(signUpActivity.this,"Enter Your Email",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {

                        Toast.makeText(signUpActivity.this,"Enter Your Password",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    SP.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {

                                        Toast.makeText(signUpActivity.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),Information_client.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(signUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
        });
    }
}