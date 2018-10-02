package com.rand.fitnesstracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MenuFunctions extends AppCompatActivity {
    public void logOff(Context context) {
        Toast.makeText(this, "Logging Off", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void viewAllAppointments(Context context) {
        Intent intent = new Intent(context, AppointmentsListActivity.class);
        intent.putExtra("CUSTOMER_ID", -1);
        context.startActivity(intent);
    }

    public void viewAllCustomers(Context context) {
        Intent intent = new Intent(context, CustomerListActivity.class);
        context.startActivity(intent);
    }
}
