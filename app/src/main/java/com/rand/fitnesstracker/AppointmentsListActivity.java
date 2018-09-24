package com.rand.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;

public class AppointmentsListActivity extends AppCompatActivity {
    private int customerID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_list);
        Bundle extras = getIntent().getExtras();

        customerID = -1;
        if (extras != null){
            customerID = extras.getInt("CUSTOMER_ID");
        }
        LinearLayout linearLayout = findViewById(R.id.appointments_linear_layout);
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler(this, null, null, 1);
        Appointment[] appointments = appointmentDBHandler.getAllAppointments(customerID);
        CustomerDBHandler customerDBHandler = new CustomerDBHandler(this, null, null, 1);
        if (customerID != -1){
            Customer customer = customerDBHandler.findCustomer(customerID);
            setTitle(customer.getFirstName() + " " + customer.getLastName());
        }
        Arrays.sort(appointments, new SortByNameAppointment());
        for (Appointment appointment : appointments){
            String buttonText = "";
            Customer currentCustomer;
            if (customerID == -1){
                currentCustomer = customerDBHandler.findCustomer(appointment.getCustomerID());
                buttonText = currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + " - ";
            }
            Button button = new Button(this);
            buttonText += appointment.getAppointmentTime().toString();
            button.setText(buttonText);
            final int APPOINTMENT_ID = appointment.getId();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AppointmentsListActivity.this, AppointmentViewActivity.class);
                    intent.putExtra("APPOINTMENT_ID", APPOINTMENT_ID);
                    startActivity(intent);
                }
            });
            linearLayout.addView(button);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(customerID != -1){
            inflater.inflate(R.menu.customer_appointments_menu, menu);
        }
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        MenuFunctions menuFunctions = new MenuFunctions();
        switch (item.getItemId()) {
            case R.id.new_appointment:
                Intent intent = new Intent(this, AppointmentEditActivity.class);
                intent.putExtra("CUSTOMER_ID", customerID);
                intent.putExtra("APPOINTMENT_ID", -1);
                startActivity(intent);
                return true;
            case R.id.log_off:
                menuFunctions.logOff();
                return true;
            case R.id.view_appointments:
                menuFunctions.viewAllAppointments();
                return true;
            case R.id.view_customers:
                menuFunctions.viewAllCustomers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
