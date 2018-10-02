package com.rand.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class AppointmentViewActivity extends AppCompatActivity {

    private int customerID;
    private int appointmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_view);
        Bundle extras = getIntent().getExtras();

        appointmentID = -1;
        if (extras != null) {
            appointmentID = extras.getInt("APPOINTMENT_ID");
        }
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler(this, null, null, 1);
        Appointment appointment = appointmentDBHandler.findAppointment(appointmentID);
        customerID = appointment.getCustomerID();
        CustomerDBHandler customerDBHandler = new CustomerDBHandler(this, null, null, 1);
        Customer customer = customerDBHandler.findCustomer(customerID);
        TextView viewName = findViewById(R.id.view_customer_name);
        viewName.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
        TextView viewTime = findViewById(R.id.view_appointment_time);
        viewTime.setText(appointment.getAppointmentTime().toString());
        TextView viewLocation = findViewById(R.id.view_location);
        viewLocation.setText(appointment.getLocation());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    public void editAppointmentClick(View view) {
        Intent intent = new Intent(this, AppointmentEditActivity.class);
        intent.putExtra("CUSTOMER_ID", customerID);
        intent.putExtra("APPOINTMENT_ID", appointmentID);
        startActivity(intent);
    }

    public void viewCustomerClick(View view) {
        Intent intent = new Intent(this, CustomerViewActivity.class);
        intent.putExtra("CUSTOMER_ID", customerID);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        MenuFunctions menuFunctions = new MenuFunctions();
        switch (item.getItemId()) {
            case R.id.log_off:
                menuFunctions.logOff(this);
                return true;
            case R.id.view_appointments:
                menuFunctions.viewAllAppointments(this);
                return true;
            case R.id.view_customers:
                menuFunctions.viewAllCustomers(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
