package com.example.ia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        dbHelper = new DatabaseHelper(this);

        TextView textViewId = findViewById(R.id.textViewId);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        TextView textViewUsername = findViewById(R.id.textViewUsername);

        // New TextViews for Additional Details
        TextView textViewAddress = findViewById(R.id.textViewAddress);
        TextView textViewAge = findViewById(R.id.textViewAge);
        TextView textViewAadharNumber = findViewById(R.id.textViewAadharNumber);
        TextView textViewFathersName = findViewById(R.id.textViewFathersName);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // Fetch user details from the database
        Cursor cursor = dbHelper.getUserDetails(username);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_REGISTRATION));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE_NUMBER));
            @SuppressLint("Range") String registeredUsername = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME_REGISTRATION));

            // Additional Details
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));
            @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_AGE));
            @SuppressLint("Range") long aadharNumber = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_AADHAR_NUMBER));
            @SuppressLint("Range") String fathersName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FATHERS_NAME));

            // Set the details to TextViews
            textViewId.setText("ID: " + id);
            textViewName.setText("Name: " + name);
            textViewPhoneNumber.setText("Phone Number: " + phoneNumber);
            textViewUsername.setText("Username: " + registeredUsername);

            // Set Additional Details
            textViewAddress.setText("Address: " + address);
            textViewAge.setText("Age: " + age);
            textViewAadharNumber.setText("Aadhar Number: " + aadharNumber);
            textViewFathersName.setText("Father's Name: " + fathersName);
        }

        // Close the cursor when done
        cursor.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Go back to NextPageActivity when pressing the back button
        Intent nextIntent = new Intent(ProfileActivity.this, NextPageActivity.class);
        startActivity(nextIntent);
        finish(); // Close the current activity
    }
}
