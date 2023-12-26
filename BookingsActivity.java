package com.example.ia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings);

        dbHelper = new DatabaseHelper(this);

        TextView textViewUsername = findViewById(R.id.textViewUsername);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        ListView listViewBookings = findViewById(R.id.listViewBookings); // Updated to use ListView
        LinearLayout linearLayoutNoBookings = findViewById(R.id.linearLayoutNoBookings);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // Fetch bookings for the user
        Cursor cursor = dbHelper.getBookingsByUsername(username);

        StringBuilder bookingsText = new StringBuilder();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String appointmentDetails = cursor.getString(cursor.getColumnIndex(dbHelper.getAppointmentDetailsColumnName()));
                @SuppressLint("Range")
                String date = cursor.getString(cursor.getColumnIndex(dbHelper.getDateColumnName()));

                bookingsText.append("Appointment: ").append(appointmentDetails)
                        .append(", Date: ").append(date).append("\n");
            } while (cursor.moveToNext());
        }

        // Display bookings in the TextView
        textViewUsername.setText(username);
        textViewTitle.setText("Your Appointments");

        if (bookingsText.length() > 0) {
            // If there are bookings, show them in the ListView
            listViewBookings.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookingsText.toString().split("\n")));
            linearLayoutNoBookings.setVisibility(View.GONE); // Hide the "No bookings yet" layout
        } else {
            // If there are no bookings, show "No bookings yet" in bold big letters
            linearLayoutNoBookings.setVisibility(View.VISIBLE);
        }

        // Close the cursor when done
        cursor.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Go back to NextPageActivity when pressing the back button
        Intent nextIntent = new Intent(BookingsActivity.this, NextPageActivity.class);
        startActivity(nextIntent);
        finish(); // Close the current activity
    }
}
