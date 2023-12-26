package com.example.ia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextAddress = findViewById(R.id.editTextAddress);
        EditText editTextAge = findViewById(R.id.editTextAge);
        EditText editTextAadharNumber = findViewById(R.id.editTextAadharNumber);
        EditText editTextFathersName = findViewById(R.id.editTextFathersName);

        Button buttonRegistration = findViewById(R.id.buttonRegistration);
        Button buttonLogin = findViewById(R.id.buttonLogin);


        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Register a new user
                String name = editTextName.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String address = editTextAddress.getText().toString();
                String ageStr = editTextAge.getText().toString();
                String aadharNumberStr = editTextAadharNumber.getText().toString();
                String fathersName = editTextFathersName.getText().toString();

                // Validate other fields as needed

                // Convert age and Aadhar number to integers
                int age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);
                long aadharNumber = aadharNumberStr.isEmpty() ? 0 : Long.parseLong(aadharNumberStr);

                long registrationResult = dbHelper.addRegistration(name, phoneNumber, username, password, address, age, aadharNumber, fathersName);

                if (registrationResult != -1) {
                    // Registration successful
                    Toast.makeText(MainActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Automatically redirect to LoginActivity after successful registration
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish(); // Close the current activity
                } else {
                    // Registration failed
                    Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Redirect to LoginActivity when clicking the Login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // Close the current activity
            }
        });



    }

}
