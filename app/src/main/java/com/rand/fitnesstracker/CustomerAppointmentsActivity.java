package com.rand.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class CustomerAppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_appointments);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_appointments_menu, menu);
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    @SuppressWarnings("unused")
    public void sampleAppointmentClick(View view){
        Intent intent = new Intent(this, AppointmentViewActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log_off:
                Toast.makeText(getApplicationContext(), "Logging Off", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.view_appointments:
                intent = new Intent(this, AppointmentsListActivity.class);
                startActivity(intent);
                return true;
            case R.id.view_customers:
                intent = new Intent(this, CustomerListActivity.class);
                startActivity(intent);
                return true;
            case R.id.new_appointment:
                intent = new Intent(this, AppointmentEditActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
