package com.example.lab4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {

    TextView adminListUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize the TextView to display users
        adminListUsers = findViewById(R.id.adminListUsers);

        // Retrieve the stored users from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String storedUsers = sharedPreferences.getString("users", "");

        // Format and display the list of cuz its freaky in prefrences
        if (!storedUsers.isEmpty()) {
            String[] usersArray = storedUsers.split(";");
            StringBuilder formattedUsers = new StringBuilder();

            for (String user : usersArray) {
                if (!user.isEmpty()) {
                    String[] userDetails = user.split(":");
                    String username = userDetails[0];
                    String role = userDetails[2];

                    // Append user details in a formatted manner
                    formattedUsers.append("Username: ").append(username)
                            .append(", Role: ").append(role)
                            .append("\n");
                }
            }

            // Set the formatted user list to the TextView
            adminListUsers.setText(formattedUsers.toString());
        } else {
            adminListUsers.setText("No users found.");
        }
    }
}