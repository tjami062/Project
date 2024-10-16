package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText usernameEditText, nameEditText, phoneEditText, passwordEditText;
    Button registerButton;
    CheckBox lessorCheckBox, renterCheckBox;

    // Email pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Init UI components
        usernameEditText = findViewById(R.id.registerTextUname);
        nameEditText = findViewById(R.id.registerTextName);
        phoneEditText = findViewById(R.id.registerTextPhone);
        passwordEditText = findViewById(R.id.registerTextPassword);
        registerButton = findViewById(R.id.buttonCreate);
        lessorCheckBox = findViewById(R.id.registerLessor);
        renterCheckBox = findViewById(R.id.registerRenter);

        // Register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String accountType = "";

                // Validate the fields
                if (!validateUsername(username)) {
                    Toast.makeText(Register.this, "Invalid username", Toast.LENGTH_SHORT).show();
                    return;
                }

                //if (!validateEmail(username)) {  // Assuming email is used as username
                //    Toast.makeText(Register.this, "Invalid email", Toast.LENGTH_SHORT).show();
                //   return;
                //}

                if (name.isEmpty()) {
                    Toast.makeText(Register.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!validatePhone(phone)) {
                    Toast.makeText(Register.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!validatePassword(password)) {
                    Toast.makeText(Register.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!lessorCheckBox.isChecked() && !renterCheckBox.isChecked()) {
                    Toast.makeText(Register.this, "Please select Lessor or Renter", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If Lessor or Renter is selected
                if (lessorCheckBox.isChecked()) {
                    accountType = "Lessor";
                } else if (renterCheckBox.isChecked()) {
                    accountType = "Renter";
                }

                // Save user data
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putString("accountType", accountType);
                editor.apply();  // Apply changes

                // Add user to users list in SharedPreferences
                String existingUsers = sharedPreferences.getString("users", "");
                String newUser = username + ":" + password + ":" + accountType + ";";
                String updatedUsers = existingUsers + newUser;
                editor.putString("users", updatedUsers);
                editor.apply();

                Toast.makeText(Register.this, "Profile Created as " + accountType + "!", Toast.LENGTH_SHORT).show();

                // Go to sign-in screen
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Validate username
    private boolean validateUsername(String username) {
        return username.length() >= 3;
    }

    // Validate email format
    private boolean validateEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Validate phone number
    private boolean validatePhone(String phone) {
        return phone.matches("\\d{10}");
    }

    // Validate password strength (min 6 characters)
    private boolean validatePassword(String password) {
        return password.length() >= 6;
    }
}