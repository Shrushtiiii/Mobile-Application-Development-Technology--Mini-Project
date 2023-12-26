package com.example.ia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username and password
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Validate the username and password against the database
                boolean loginSuccessful = dbHelper.checkLogin(username, password);

                if (!username.isEmpty() && loginSuccessful) {
                    // If valid, proceed to the next page
                    Intent nextIntent = new Intent(LoginActivity.this, NextPageActivity.class);
                    nextIntent.putExtra("username", username);
                    startActivity(nextIntent);
                    finish(); // Close the login activity
                } else {
                    // Show an error message or handle invalid login
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    // Add onBackPressed method
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Go back to MainActivity when pressing the back button
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish(); // Close the current activity
    }
}
