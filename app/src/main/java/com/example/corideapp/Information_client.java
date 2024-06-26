package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Information_client extends AppCompatActivity {

    private EditText editU, editA, editP;
    private Button continueButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    RadioGroup radioGroup;
    private RadioButton op1,op2;
    String address,phone,surname,userId;
    private String gender;
    private Database2 databasehelper;



    // ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_client);

        // Finding the continue button
        continueButton = findViewById(R.id.button);
        radioGroup = findViewById(R.id.radioGroup);
        editU = findViewById(R.id.inputName);
        editA = findViewById(R.id.inputAdress);
        editP = findViewById(R.id.inputPhone);
        op1 = findViewById(R.id.buttonMale);
        op2 = findViewById(R.id.buttonFemale);


        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        databasehelper = new Database2(this);


        editU.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // If the EditText loses focus
                    String username = editU.getText().toString().trim();
                    if (TextUtils.isEmpty(username)) {
                        // If the EditText is empty, display a Toast
                        Toast.makeText(Information_client.this, "You Need To Enter A User Name", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        editA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // If the EditText loses focus
                    String address1 = editA.getText().toString().trim();
                    if (TextUtils.isEmpty(address1)) {
                        // If the EditText is empty, display a Toast
                        Toast.makeText(Information_client.this, "You Need To Enter An Address", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        editP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // If the EditText loses focus
                    String phone1 = editP.getText().toString().trim();
                    if (TextUtils.isEmpty(phone1)) {
                        // If the EditText is empty, display a Toast
                        Toast.makeText(Information_client.this, "You Need TO Enter A Phone Number", Toast.LENGTH_SHORT).show();
                    }else if (!phone1.matches("\\d{8}")) {
                        // If the input is not exactly 8 numbers, display a Toast
                        Toast.makeText(Information_client.this, "The phone  must be 8 digits long", Toast.LENGTH_SHORT).show();
                        // Clear the EditText field
                        editU.setText("");
                    }
                }
            }
        });

        // Setting OnClickListener for the continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initialize the validation status to true
                boolean isValid = true;
                // Validation du nom
                address = editA.getText().toString().trim();
                phone = editP.getText().toString().trim();
                surname = editU.getText().toString().trim();


                // Validation du prénom
                surname = editU.getText().toString().trim();
                if (surname.isEmpty() || !surname.matches(".*[a-zA-Z].*")) {
                    // Display toast message for invalid surname
                    Toast.makeText(Information_client.this, R.string.surname_validation_message, Toast.LENGTH_SHORT).show();
                    isValid = false;
                    return;
                }

                if (address.isEmpty()) {
                    // Display toast message for empty address
                    Toast.makeText(Information_client.this, R.string.name_validation_message, Toast.LENGTH_SHORT).show();
                    isValid = false;
                    return;
                }


                // Validation de l'e-mail
                if (phone.isEmpty()) {
                    // Display toast message for empty phone number
                    Toast.makeText(Information_client.this, R.string.email_validation_message, Toast.LENGTH_SHORT).show();
                    isValid = false;
                    return;
                } else if (!TextUtils.isDigitsOnly(phone) || phone.length() != 8) {
                    // Display toast message for invalid phone number
                    Toast.makeText(Information_client.this, "Phone number must be 8 digits and contain only numbers", Toast.LENGTH_SHORT).show();
                    isValid = false;
                    return;
                }


                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    // No RadioButton is selected
                    Toast.makeText(Information_client.this, "Please select a gender option", Toast.LENGTH_SHORT).show();
                    isValid = false;
                    return;
                }
                // If all validations pass, continue with desired action (e.g., navigating to another activity)

                userId = mAuth.getCurrentUser().getUid();
                currentUser = mAuth.getCurrentUser();

                if (isValid) {
                    userId = mAuth.getCurrentUser().getUid();
                    currentUser = mAuth.getCurrentUser();

                    if (currentUser != null) {
                        DatabaseReference userRef = mDatabase.child(userId);

                        // Write the user data to Firebase
                        writeAdditionalUserInfo(userRef, address, phone, surname);
                    }
                }
                addData();
            }
        });


    }
    public void onSave(View view){
        if(op1.isChecked()){
            gender="Male";
        }else if(op2.isChecked()){
            gender = "Female";
        }else{
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void writeAdditionalUserInfo(DatabaseReference userRef, String address, String phone, String surname) {
        // Create a Map to store additional user information
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("address", address);
        additionalInfo.put("phone", phone);
        additionalInfo.put("surname", surname);

        // Write the additional information to the database
        userRef.updateChildren(additionalInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully added
                        Toast.makeText(Information_client.this, "Additional information added successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Information_client.this, Home.class);
                        startActivity(intent);
                        // Optionally, go to the next activity or do something else
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        Toast.makeText(Information_client.this, "Failed to add additional information.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void addData(){


        boolean isInserted = databasehelper.insertUserData(editU.getText().toString(),editA.getText().toString(),editP.getText().toString(), gender) ;
        if (isInserted){
            Toast.makeText(Information_client.this,"Data Inserted Yaatiik Saha",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(Information_client.this,"Something went wrong Lahtha barka",Toast.LENGTH_SHORT).show();

        }

    }
    private boolean isValidName(String name) {
        // Ensure that the name contains only letters and spaces
        boolean containsOnlyLettersAndSpaces = name.matches("[a-zA-Z ]+");

        // Return true if the name is valid, false otherwise
        return containsOnlyLettersAndSpaces;
    }
}
