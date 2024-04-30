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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class signUpActivity extends AppCompatActivity {


    private EditText editTextEmail , editTextPassword1 , editTextName, editTextPassword2;
    private Button signUpB;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressBar SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        signUpB = findViewById(R.id.signUpButton);
        editTextName = findViewById(R.id.inputN);
        editTextEmail = findViewById(R.id.inputE2);
        editTextPassword1 = findViewById(R.id.inputP2);
        editTextPassword2 = findViewById(R.id.inputP3);
        SP = findViewById(R.id.progressBarSignUp);

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SP.setVisibility(View.VISIBLE);
                String email,name , password, password2;
                email = editTextEmail.getText().toString();
                name = editTextName.getText().toString();
                password = editTextPassword1.getText().toString().trim();
                password2 = editTextPassword2.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(signUpActivity.this,"Enter Your Email",Toast.LENGTH_SHORT).show();
                    SP.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
                    Toast.makeText(signUpActivity.this, "Please enter both passwords.", Toast.LENGTH_SHORT).show();
                    SP.setVisibility(View.GONE);
                    return;
                }
                else if (!password.equals(password2)) {
                    Toast.makeText(signUpActivity.this, "Passwords do not match. Please re-enter.", Toast.LENGTH_SHORT).show();
                    // Clear the EditText fields for re-entering the passwords
                    editTextPassword1.setText("");
                    editTextPassword2.setText("");
                    SP.setVisibility(View.GONE);
                    return;
                } else {
                    // Passwords match, proceed with sign-up logic
                    Toast.makeText(signUpActivity.this, "Passwords match. Proceed with sign-up.", Toast.LENGTH_SHORT).show();
                    // Add your sign-up logic here
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                SP.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // User sign-up successful, now add additional information to the database
                                    String userId = mAuth.getCurrentUser().getUid();
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
                                    // Store additional user information in the database
                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("name", name);
                                    userInfo.put("email", email);
                                    // Add more information if needed
                                    userRef.setValue(userInfo);
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
    public void writeNewUser(String userId, String name, String email) {
        user user = new user(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
}