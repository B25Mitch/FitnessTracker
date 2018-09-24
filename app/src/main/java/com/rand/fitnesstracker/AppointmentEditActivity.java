package com.rand.fitnesstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Timestamp;
import java.util.Calendar;

public class AppointmentEditActivity extends AppCompatActivity {
    private int customerID;
    private int appointmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_edit);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            customerID = extras.getInt("CUSTOMER_ID");
            appointmentID = extras.getInt("APPOINTMENT_ID");
        }

        CustomerDBHandler customerDBHandler = new CustomerDBHandler(this, null, null, 1);
        if (appointmentID != -1) {
            AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler(this, null, null, 1);
            Appointment appointment = appointmentDBHandler.findAppointment(appointmentID);
            DatePicker datePicker = findViewById(R.id.appointment_date_picker);
            Calendar cal = Calendar.getInstance();
            cal.setTime(appointment.getAppointmentTime());
            datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            TimePicker timePicker = findViewById(R.id.appointment_time_picker);
            timePicker.setIs24HourView(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(cal.get(Calendar.HOUR));
            }else{
                timePicker.setCurrentHour(cal.get(Calendar.HOUR));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setMinute(cal.get(Calendar.MINUTE));
            } else{
                timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
            }
            EditText editLocation = findViewById(R.id.edit_location);
            editLocation.setText(appointment.getLocation());

        }
        Customer customer = customerDBHandler.findCustomer(customerID);
        EditText editCustomerName = findViewById(R.id.edit_appointment_customer);
        editCustomerName.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appointment_edit_menu, menu);
        return true;
    }

    @SuppressWarnings("unused")
    public void okClick(View view){
        AppointmentDBHandler dbHandler = new AppointmentDBHandler(this, null, null, 1);
        DatePicker datePicker = findViewById(R.id.appointment_date_picker);
        TimePicker timePicker = findViewById(R.id.appointment_time_picker);
        Calendar cal = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
        }else{
            cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        }
        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        EditText editLocation = findViewById(R.id.edit_location);
        int newID = appointmentID;
        Appointment appointment = new Appointment(timestamp, customerID, editLocation.getText().toString());
        if (appointmentID != -1){
            dbHandler.modifyAppointment(appointment);
        }
        else{
            newID = dbHandler.addAppointment(appointment);
        }
        Intent intent = new Intent(this, AppointmentViewActivity.class);
        intent.putExtra("CUSTOMER_ID", customerID);
        intent.putExtra("APPOINTMENT_ID", newID);
        startActivity(intent);
    }

    @SuppressWarnings("unused")
    public void cancelClick(View view){
        if (appointmentID != -1) {
            Intent intent = new Intent(this, AppointmentViewActivity.class);
            intent.putExtra("CUSTOMER_ID", customerID);
            intent.putExtra("APPOINTMENT_ID", appointmentID);
            startActivity(intent);
        }
        Intent intent = new Intent(this, AppointmentsListActivity.class);
        intent.putExtra("CUSTOMER_ID", customerID);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_appointment:
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(AppointmentEditActivity.this);
                myAlertDialog.setTitle("Delete");
                myAlertDialog.setMessage("Are you sure you want to delete?");
                myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        AppointmentDBHandler dbHandler = new AppointmentDBHandler(AppointmentEditActivity.this, null, null, 1);
                        dbHandler.deleteAppointment(appointmentID);
                        Intent intent = new Intent(AppointmentEditActivity.this, AppointmentsListActivity.class);
                        intent.putExtra("CUSTOMER_ID", customerID);
                        startActivity(intent);
                    }});
                myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        // do nothing
                    }});
                myAlertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
