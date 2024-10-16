package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreen extends AppCompatActivity {

    TextView welcomeUserDisplay, welcomeRoleDisplay;
    Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // Init UI components
        welcomeUserDisplay = findViewById(R.id.welcomeUserDisplay);
        welcomeRoleDisplay = findViewById(R.id.welcomeRoleDisplay);
        signOutButton = findViewById(R.id.welcomeSignOut);

        // Get user data
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        String role = sharedPreferences.getString("accountType", "Unknown");

        // Set role display
        welcomeUserDisplay.setText("Welcome, " + username + "!");
        welcomeRoleDisplay.setText("Role: " + role);

        // sign out button click
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the Sign-In
                Intent intent = new Intent(WelcomeScreen.this, MainActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });
    }
}