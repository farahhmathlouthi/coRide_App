package com.example.corideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity3_signin extends AppCompatActivity {

    private EditText passwordEditText;
    private ImageButton togglePasswordVisibilityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        passwordEditText = findViewById(R.id.editTextTextPassword);
        togglePasswordVisibilityButton = findViewById(R.id.togglePasswordVisibility);

        togglePasswordVisibilityButton.setOnClickListener(v -> togglePasswordVisibility());

        findViewById(R.id.singInButtom).setOnClickListener(v -> {
            String password = passwordEditText.getText().toString();
            if (isValidPassword(password)) {
                Toast.makeText(MainActivity3_signin.this, "Password is valid.", Toast.LENGTH_SHORT).show();
                // Proceed with sign-in logic
            } else {
                Toast.makeText(MainActivity3_signin.this, "Password must contain at least one digit, one uppercase letter, one lowercase letter, one special character, and be at least 6 characters long.", Toast.LENGTH_LONG).show();
                // Prompt the user to enter a valid password
            }
        });
    }

    private boolean isValidPassword(String password) {
        // Password must contain at least one digit
        Pattern digitPattern = Pattern.compile(".*\\d.*");
        Matcher digitMatcher = digitPattern.matcher(password);
        // Password must contain at least one uppercase letter
        Pattern upperCasePattern = Pattern.compile(".*[A-Z].*");
        Matcher upperCaseMatcher = upperCasePattern.matcher(password);
        // Password must contain at least one lowercase letter
        Pattern lowerCasePattern = Pattern.compile(".*[a-z].*");
        Matcher lowerCaseMatcher = lowerCasePattern.matcher(password);
        // Password must contain at least one special character
        Pattern specialCharPattern = Pattern.compile(".*[!@#$%^&*()\\-+].*");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);
        // Password must be at least 6 characters long
        boolean isLengthValid = password.length() >= 6;
        // Check if all conditions are met
        return digitMatcher.matches() && upperCaseMatcher.matches() && lowerCaseMatcher.matches()
                && specialCharMatcher.matches() && isLengthValid;
    }

    private void togglePasswordVisibility() {
        if (passwordEditText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            // Show password
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // Hide password
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        // Move the cursor to the end of the text
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

}
