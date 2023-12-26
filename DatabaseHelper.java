package com.example.ia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Clinic";
    private static final int DATABASE_VERSION = 2;

    // Registration Table
    private static final String TABLE_REGISTRATION = "registration";
    public static final String COLUMN_ID_REGISTRATION = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_USERNAME_REGISTRATION = "username";
    private static final String COLUMN_PASSWORD = "password";

   public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_AADHAR_NUMBER = "aadhar_number";
    public static final String COLUMN_FATHERS_NAME = "fathers_name";




    // Appointments Table
    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COLUMN_ID_APPOINTMENTS = "id";
    public static final String COLUMN_USERNAME_APPOINTMENTS = "username";
    public static final String COLUMN_APPOINTMENT_DETAILS = "appointment_details";
    public static final String COLUMN_DATE = "date";

    public String getAppointmentDetailsColumnName() {
        return COLUMN_APPOINTMENT_DETAILS;
    }

    // Public method to get date column name
    public String getDateColumnName() {
        return COLUMN_DATE;
    }
    // Create Registration Table
    // Updated Create Registration Table Query
    private static final String CREATE_TABLE_REGISTRATION =
            "CREATE TABLE " + TABLE_REGISTRATION + " (" +
                    COLUMN_ID_REGISTRATION + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PHONE_NUMBER + " TEXT," +
                    COLUMN_USERNAME_REGISTRATION + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_ADDRESS + " TEXT," +
                    COLUMN_AGE + " INTEGER," +
                    COLUMN_AADHAR_NUMBER + " INTEGER," +
                    COLUMN_FATHERS_NAME + " TEXT)";

    // Create Appointments Table
    private static final String CREATE_TABLE_APPOINTMENTS =
            "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                    COLUMN_ID_APPOINTMENTS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME_APPOINTMENTS + " TEXT," +
                    COLUMN_APPOINTMENT_DETAILS + " TEXT," +
                    COLUMN_DATE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REGISTRATION);
        db.execSQL(CREATE_TABLE_APPOINTMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    // Registration Table Operations

    // Updated addRegistration Method
    public long addRegistration(String name, String phoneNumber, String username, String password,
                                String address, int age, long aadharNumber, String fathersName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_USERNAME_REGISTRATION, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_AADHAR_NUMBER, aadharNumber);  // Use the correct column name here
        values.put(COLUMN_FATHERS_NAME, fathersName);

        try {
            // Try to insert the values into the database
            return db.insertOrThrow(TABLE_REGISTRATION, null, values);
        } catch (SQLException e) {
            // Log the error for debugging
            Log.e("DatabaseHelper", "Error inserting data into registration table", e);
            return -1; // Return -1 to indicate failure
        } finally {
            // Close the database connection
            db.close();
        }
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTRATION +
                " WHERE " + COLUMN_USERNAME_REGISTRATION + " = ? AND " +
                COLUMN_PASSWORD + " = ?", new String[]{username, password});

        // Check if cursor has at least one row
        boolean result = cursor.moveToFirst();

        // Close cursor and database connection
        cursor.close();
        db.close();

        return result;
    }

    // Appointments Table Operations

    public long addAppointment(String username, String appointmentDetails, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME_APPOINTMENTS, username);
        values.put(COLUMN_APPOINTMENT_DETAILS, appointmentDetails);
        values.put(COLUMN_DATE, date);
        return db.insert(TABLE_APPOINTMENTS, null, values);
    }

    public Cursor getBookingsByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_APPOINTMENTS +
                " WHERE " + COLUMN_USERNAME_APPOINTMENTS + " = ?", new String[]{username});
    }

    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_REGISTRATION +
                " WHERE " + COLUMN_USERNAME_REGISTRATION + " = ?", new String[]{username});
    }
    public String getAppointmentTypeFromTag(String tag) {
        return tag;
    }
}
