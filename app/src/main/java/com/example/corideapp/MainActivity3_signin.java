package com.example.corideapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity3_signin extends AppCompatActivity {

    private EditText passwordEditText;
    private Drawable showPasswordDrawable;
    private boolean passwordVisible = false;
    private ImageButton togglePasswordVisibilityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        passwordEditText = findViewById(R.id.inputP1);
        // Get the drawable used for toggling password visibility
        showPasswordDrawable = getResources().getDrawable(R.drawable.dossiers);
        // Set the bounds for the drawable
        showPasswordDrawable.setBounds(0, 0, showPasswordDrawable.getIntrinsicWidth(), showPasswordDrawable.getIntrinsicHeight());
        // Set the compound drawable to the right of the EditText
        passwordEditText.setCompoundDrawables(null, null, showPasswordDrawable, null);

        passwordEditText.setOnTouchListener((v, event) -> {
            // Check if the touch event is inside the bounds of the drawable
            if (event.getAction() == MotionEvent.ACTION_UP && event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[2].getBounds().width())) {
                // Toggle password visibility
                togglePasswordVisibility();
                return true;
            }
            return false;
        });

        findViewById(R.id.singInButton).setOnClickListener(v -> {
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
        if (passwordVisible) {
            // Hide password
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordVisible = false;
            // Update drawable to show the "show password" icon
            showPasswordDrawable = getResources().getDrawable(R.drawable.dossiers);
        } else {
            // Show password
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordVisible = true;
            // Update drawable to show the "hide password" icon
            showPasswordDrawable = getResources().getDrawable(R.drawable.dossiers);
        }
        // Set bounds for the new drawable
        showPasswordDrawable.setBounds(0, 0, showPasswordDrawable.getIntrinsicWidth(), showPasswordDrawable.getIntrinsicHeight());
        // Set the new drawable to the right of the EditText
        passwordEditText.setCompoundDrawables(null, null, showPasswordDrawable, null);
    }
}
