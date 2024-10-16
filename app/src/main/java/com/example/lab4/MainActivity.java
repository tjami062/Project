package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button signInButton, createAccountButton;

    // Admin credentials
    private final String adminUsername = "admin";
    private final String adminPassword = "XPI76SZUqyCjVxgnUjm0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init UI components
        usernameEditText = findViewById(R.id.signInUnameText);
        passwordEditText = findViewById(R.id.signInPasswordText);
        signInButton = findViewById(R.id.signInbutton);
        createAccountButton = findViewById(R.id.signInButtonAccount);

        // Check inputs, compare with admin and preferences
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                // Check for admin credentials first
                if (enteredUsername.equals(adminUsername) && enteredPassword.equals(adminPassword)) {
                    // Admin login successful, navigate to AdminDashboard
                    Toast.makeText(MainActivity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Check for regular user credentials
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                    String storedUsers = sharedPreferences.getString("users", "");

                    // Split the stored users by semicolon and check each user
                    String[] usersArray = storedUsers.split(";");
                    boolean found = false;
                    for (String user : usersArray) {
                        if (!user.isEmpty()) {
                            String[] userDetails = user.split(":");
                            String storedUsername = userDetails[0];
                            String storedPassword = userDetails[1];

                            // Check if entered username and password match
                            if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword)) {
                                found = true;
                                break;
                            }
                        }
                    }

                    if (found) {
                        // Credentials are valid, proceed to the welcome screen
                        Intent intent = new Intent(MainActivity.this, WelcomeScreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Invalid credentials
                        Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Create Account Button - Navigate to Register activity
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
