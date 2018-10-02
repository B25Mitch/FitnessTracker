package com.rand.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;

class AppointmentDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "appointmentDB.db";
    private static final String TABLE_APPOINTMENTS = "appointments";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_APPOINTMENT_TIME = "appointmentTime";
    private static final String COLUMN_CUSTOMER_ID = "customerID";
    private static final String COLUMN_LOCATION = "location";

    @SuppressWarnings("unused")
    AppointmentDBHandler(Context context, String name,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_APPOINTMENTS_TABLE = "CREATE TABLE " +
                TABLE_APPOINTMENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_APPOINTMENT_TIME + " DATETIME, " +
                COLUMN_CUSTOMER_ID + " INTEGER, " +
                COLUMN_LOCATION + " TEXT)";
        db.execSQL(CREATE_APPOINTMENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    int addAppointment(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_APPOINTMENT_TIME, appointment.getAppointmentTime().toString());
        values.put(COLUMN_CUSTOMER_ID, appointment.getCustomerID());
        values.put(COLUMN_LOCATION, appointment.getLocation());

        SQLiteDatabase db = this.getWritableDatabase();
        int returnValue = (int) db.insert(TABLE_APPOINTMENTS, null, values);
        db.close();
        return returnValue;
    }

    Appointment findAppointment(int id) {
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {Integer.toString(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("TAG1", "findAppointment");
        Cursor cursor = db.query(TABLE_APPOINTMENTS, null, selection, selectionArgs, null, null, null);
        Appointment appointment = new Appointment();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            appointment.setId(cursor.getInt(0));
            appointment.setAppointmentTime(Timestamp.valueOf(cursor.getString(1)));
            appointment.setCustomerID(cursor.getInt(2));
            appointment.setLocation(cursor.getString(3));
            cursor.close();
        } else {
            appointment = null;
        }
        db.close();
        return appointment;
    }

    void deleteAppointment(int appointmentID) {
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {Integer.toString(appointmentID)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPOINTMENTS, selection, selectionArgs);
        db.close();
    }

    Appointment[] getAllAppointments(int customerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = null;
        String[] selectionArgs = null;
        if (customerID != -1) {
            selection = COLUMN_CUSTOMER_ID + " = ?";
            selectionArgs = new String[]{Integer.toString(customerID)};
        }
        Cursor cursor = db.query(TABLE_APPOINTMENTS, null, selection, selectionArgs, null, null, null);
        int index = 0;
        Appointment[] appointments = new Appointment[cursor.getCount()];
        while (cursor.moveToNext()) {
            appointments[index] = (findAppointment(Integer.parseInt(cursor.getString(0))));
            index++;
        }
        cursor.close();
        return appointments;
    }

    void modifyAppointment(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, appointment.getLocation());
        values.put(COLUMN_APPOINTMENT_TIME, appointment.getAppointmentTime().toString());
        values.put(COLUMN_CUSTOMER_ID, appointment.getCustomerID());
        String selection = COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] comparison = {Integer.toString(appointment.getId())};
        db.update(TABLE_APPOINTMENTS, values, selection, comparison);
        db.close();
    }
}
