package com.rand.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MenuFunctions extends AppCompatActivity {
    public void logOff() {
        Toast.makeText(getApplicationContext(), "Logging Off", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void viewAllAppointments() {
        Intent intent = new Intent(this, AppointmentsListActivity.class);
        intent.putExtra("CUSTOMER_ID", -1);
        startActivity(intent);
    }

    public void viewAllCustomers() {
        Intent intent = new Intent(this, CustomerListActivity.class);
        startActivity(intent);
    }
}
