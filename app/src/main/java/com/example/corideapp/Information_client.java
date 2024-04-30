package com.example.corideapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    String address,phone,surname;
    // ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_client);


        // Finding the continue button
        continueButton = findViewById(R.id.button);
        editU = findViewById(R.id.inputName);
        editA = findViewById(R.id.inputAdress);
        editP = findViewById(R.id.inputPhone);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        currentUser = mAuth.getCurrentUser();

        // Setting OnClickListener for the continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validation du nom
                address = editA.getText().toString().trim();
                phone = editP.getText().toString().trim();
                surname = editU.getText().toString().trim();

                if (address.isEmpty() ) {
                    // Afficher le message de validation du nom
                    TextView addressValidationMessage = findViewById(R.id.inputAdress);
                    addressValidationMessage.setVisibility(View.VISIBLE);
                    addressValidationMessage.setText(R.string.name_validation_message);
                    return; // Stop execution if validation fails
                }

                // Validation du prénom
                surname = editU.getText().toString().trim();
                if (surname.isEmpty() || !surname.matches(".*[a-zA-Z].*")) {
                    // Afficher le message de validation du prénom
                    TextView surnameValidationMessage = findViewById(R.id.prenomcontrole);
                    surnameValidationMessage.setVisibility(View.VISIBLE);
                    surnameValidationMessage.setText(R.string.surname_validation_message);
                    return;
                }

                // Validation de l'e-mail
                if (phone.isEmpty()) {
                    // Afficher le message de validation de l'e-mail
                    TextView userNameValidationMessage = findViewById(R.id.emailcontrole);
                    userNameValidationMessage.setVisibility(View.VISIBLE);
                    userNameValidationMessage.setText(R.string.email_validation_message);
                    return;
                }

                // If all validations pass, continue with desired action (e.g., navigating to another activity)

                if (currentUser != null) {
                    String userId = currentUser.getUid();

                    // Write the user data to Firebase
                    writeAdditionalUserInfo(userId, address, phone, surname);
                }
            }
        });
    }

    private void writeAdditionalUserInfo(String userId, String address, String phone, String surname) {
        // Create a Map to store additional user information
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("address", address);
        additionalInfo.put("phone", phone);
        additionalInfo.put("surname", surname);
        // Write the additional information to the database
        mDatabase.child(userId).setValue(additionalInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully added
                        Toast.makeText(Information_client.this, "Additional information added successfully.", Toast.LENGTH_SHORT).show();
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
}
