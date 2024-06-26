package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class signUpActivity extends AppCompatActivity {


    private EditText editTextEmail , editTextPassword1 , editTextName, editTextPassword2;
    private Button signUpB;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressBar SP;
    String email,name , password, password2;
    CheckBox checkBox1;
    private Database2 databasehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        signUpB = findViewById(R.id.signUpButton);
        editTextName = findViewById(R.id.inputN);
        editTextEmail = findViewById(R.id.inputE2);
        editTextPassword1 = findViewById(R.id.inputP2);
        editTextPassword2 = findViewById(R.id.inputP3);
        SP = findViewById(R.id.progressBarSignUp);
        checkBox1 = findViewById(R.id.terms);
        databasehelper = new Database2(this);
        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // If the EditText loses focus
                    String name = editTextName.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        // If the EditText is empty, display a Toast
                        Toast.makeText(signUpActivity.this, "You Need To Enter Your Full Name", Toast.LENGTH_SHORT).show();
                    }
                    else if ( !isValidName(name)) {
                        // If the EditText is empty, display a Toast
                        Toast.makeText(signUpActivity.this, "Name can contain only letters and spaces", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // If the EditText loses focus
                    String name = editTextEmail.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        // If the EditText is empty, display a Toast
                        Toast.makeText(signUpActivity.this, "You Need To Enter Your Email", Toast.LENGTH_SHORT).show();
                    }else if (isValidEmail(name)) {
                        // If the email is not valid, the isValidEmail function will display a toast message
                        // No need to display another toast message here
                        SP.setVisibility(View.GONE);
                    }
                }
            }
        });
        editTextPassword1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // If the EditText loses focus
                    String name = editTextPassword1.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        // If the EditText is empty, display a Toast
                        Toast.makeText(signUpActivity.this, "You Need To Enter Your Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        editTextPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // If the EditText loses focus
                    String name = editTextPassword2.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        // If the EditText is empty, display a Toast
                        Toast.makeText(signUpActivity.this, "You Need TO Re-Enter Your Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextName.getText().toString().trim()) &&
                        TextUtils.isEmpty(editTextEmail.getText().toString().trim()) &&
                        TextUtils.isEmpty(editTextPassword1.getText().toString().trim()) &&
                        TextUtils.isEmpty(editTextPassword2.getText().toString().trim())) {
                    // If all fields are empty, display a Toast
                    Toast.makeText(signUpActivity.this, "All fields are empty. Please fill in the required information.", Toast.LENGTH_SHORT).show();
                    return;
                }

                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();
                password = editTextPassword1.getText().toString().trim();
                password2 = editTextPassword2.getText().toString().trim();
                int paddingTop = 100;
                if (!isValidName(name)) {
                    Toast.makeText(signUpActivity.this, "Invalid name. Name can contain only letters and spaces.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(email)) {
                    // isValidEmail() method already displays appropriate toast messages
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast toast = Toast.makeText(signUpActivity.this, "Enter Your Name", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, paddingTop);
                    toast.show();
                    SP.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(email)) {
                    Toast toast = Toast.makeText(signUpActivity.this, "Enter Your Email", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, paddingTop);
                    toast.show();
                    SP.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
                    Toast toast = Toast.makeText(signUpActivity.this, "Please enter both passwords.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, paddingTop);
                    toast.show();
                    SP.setVisibility(View.GONE);
                    return;
                } else if (!password.equals(password2)) {
                    Toast toast = Toast.makeText(signUpActivity.this, "Passwords do not match. Please re-enter.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, paddingTop);
                    toast.show();
                    // Clear the EditText fields for re-entering the passwords
                    editTextPassword1.setText("");
                    editTextPassword2.setText("");
                    SP.setVisibility(View.GONE);
                    return;
                } else if (!checkBox1.isChecked()) {
                    Toast toast = Toast.makeText(signUpActivity.this, "Please check the checkbox.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, paddingTop);
                    toast.show();
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
                                    Intent intent = new Intent(signUpActivity.this, loading3.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signUpActivity.this, "Password must have: 1 uppercase, 1 number, 1 special character, and be more than 6 characters long.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                addData();
            }

        });

    }
    public void writeNewUser(String userId, String name, String email) {
        user user = new user(name, email );
        mDatabase.child("users").child(userId).setValue(user);
    }
    public void addData(){
        boolean isInserted = databasehelper.insertUserInfoData(editTextName.getText().toString(), editTextEmail.getText().toString());
        if (isInserted) {
            Toast.makeText(signUpActivity.this, "Data Inserted Yaatiik Saha", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(signUpActivity.this, "Something went wrong Lahtha barka", Toast.LENGTH_SHORT).show();

        }

    }
    private boolean isValidName(String name) {
        // Ensure that the name contains only letters and spaces
        boolean containsOnlyLettersAndSpaces = name.matches("[a-zA-Z ]+");

        // Return true if the name is valid, false otherwise
        return containsOnlyLettersAndSpaces;
    }
    private boolean isValidEmail(String email) {
        // Check if email contains @ symbol
        if (!email.contains("@")) {
            // Display a toast indicating that the email is invalid
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if email ends with a valid domain (.com or .fr)
        if (!(email.endsWith(".com") || email.endsWith(".fr"))) {
            // Display a toast indicating that the email domain is invalid
            Toast.makeText(this, "Email domain must be .com or .fr", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if email contains a valid service provider (@gmail, @hotmail, @yahoo)
        if (!(email.contains("@gmail") || email.contains("@hotmail") || email.contains("@yahoo"))) {
            // Display a toast indicating that the email service provider is invalid
            Toast.makeText(this, "Email service provider must be Gmail, Hotmail, or Yahoo", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Email is valid if it passes all the checks
        return true;
    }



};