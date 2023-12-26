package com.example.ia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NextPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextpage);

        TextView textViewUsername = findViewById(R.id.textViewUsername);
        Button buttonProfile = findViewById(R.id.buttonProfile);
        Button buttonAppointments = findViewById(R.id.buttonAppointments);
        Button buttonBookings = findViewById(R.id.buttonBookings);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        textViewUsername.setText(username);

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // View profile details
                Intent profileIntent = new Intent(NextPageActivity.this, ProfileActivity.class);
                profileIntent.putExtra("username", username);
                startActivity(profileIntent);
            }
        });

        buttonAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Book an appointment
                Intent appointmentIntent = new Intent(NextPageActivity.this, AppointmentActivity.class);
                appointmentIntent.putExtra("username", username);
                startActivity(appointmentIntent);
            }
        });

        buttonBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // View bookings
                Intent bookingsIntent = new Intent(NextPageActivity.this, BookingsActivity.class);
                bookingsIntent.putExtra("username", username);
                startActivity(bookingsIntent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout
                Intent loginIntent = new Intent(NextPageActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // Close the current activity
            }
        });
    }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            // Go back to LoginActivity when pressing the back button
            Intent loginIntent = new Intent(NextPageActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Close the current activity
    }
}
