package com.example.corideapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Information_client extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_client);

        // Finding the continue button
        Button continueButton = findViewById(R.id.button);

        // Setting OnClickListener for the continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validation du nom
                EditText nameEditText = findViewById(R.id.inputusername);
                String name = nameEditText.getText().toString().trim();
                if (name.isEmpty() || !name.matches(".*[a-zA-Z].*")) {
                    // Afficher le message de validation du nom
                    TextView nameValidationMessage = findViewById(R.id.nomcontrole);
                    nameValidationMessage.setVisibility(View.VISIBLE);
                    nameValidationMessage.setText(R.string.name_validation_message);
                    return; // Stop execution if validation fails
                }

                // Validation du prénom
                EditText surnameEditText = findViewById(R.id.inputprenom);
                String surname = surnameEditText.getText().toString().trim();
                if (surname.isEmpty() || !surname.matches(".*[a-zA-Z].*")) {
                    // Afficher le message de validation du prénom
                    TextView surnameValidationMessage = findViewById(R.id.prenomcontrole);
                    surnameValidationMessage.setVisibility(View.VISIBLE);
                    surnameValidationMessage.setText(R.string.surname_validation_message);
                    return;
                }

                // Validation de l'e-mail
                EditText emailEditText = findViewById(R.id.inputemail);
                String email = emailEditText.getText().toString().trim();
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Afficher le message de validation de l'e-mail
                    TextView emailValidationMessage = findViewById(R.id.emailcontrole);
                    emailValidationMessage.setVisibility(View.VISIBLE);
                    emailValidationMessage.setText(R.string.email_validation_message);
                    return;
                }

                // If all validations pass, continue with desired action (e.g., navigating to another activity)
            }
        });
    }
}
