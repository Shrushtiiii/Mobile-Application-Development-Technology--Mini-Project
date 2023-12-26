package com.example.ia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ia.NextPageActivity;

public class AppointmentActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        dbHelper = new DatabaseHelper(this);

        // Initialize the textViewUsername
        textViewUsername = findViewById(R.id.textViewUsername);

        EditText editTextDateRegularCheckup = findViewById(R.id.editTextDateRegularCheckup);
        EditText editTextDateFever = findViewById(R.id.editTextDateFever);
        EditText editTextDateEyeCheckup = findViewById(R.id.editTextDateEyeCheckup);
        EditText editTextDateAccident = findViewById(R.id.editTextDateAccident);

        Button buttonBookRegularCheckup = findViewById(R.id.buttonBookRegularCheckup);
        Button buttonBookFever = findViewById(R.id.buttonBookFever);
        Button buttonBookEyeCheckup = findViewById(R.id.buttonBookEyeCheckup);
        Button buttonBookAccident = findViewById(R.id.buttonBookAccident);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // Set the username in the textViewUsername
        textViewUsername.setText(username);

        buttonBookRegularCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment(username, "Regular Checkup", editTextDateRegularCheckup);
            }
        });

        buttonBookFever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment(username, "Fever", editTextDateFever);
            }
        });

        buttonBookEyeCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment(username, "Eye Checkup", editTextDateEyeCheckup);
            }
        });

        buttonBookAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment(username, "Accident", editTextDateAccident);
            }
        });
    }

    private void bookAppointment(String username, String appointmentDetails, EditText editTextDate) {
        String date = editTextDate.getText().toString();

        if (date.isEmpty()) {
            Toast.makeText(AppointmentActivity.this, "Please enter a date", Toast.LENGTH_SHORT).show();
            return;
        }

        long appointmentResult = dbHelper.addAppointment(username, appointmentDetails, date);

        if (appointmentResult != -1) {
            // Appointment booked successfully
            Toast.makeText(AppointmentActivity.this, "Appointment booked successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Appointment booking failed
            Toast.makeText(AppointmentActivity.this, "Appointment booking failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Go back to NextPageActivity when pressing the back button
        Intent nextIntent = new Intent(AppointmentActivity.this, NextPageActivity.class);
        startActivity(nextIntent);
        finish(); // Close the current activity
    }
}
