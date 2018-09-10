package com.rand.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AppointmentEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_edit);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appointment_edit_menu, menu);
        return true;
    }

    public void okClick(View view){
        Intent intent = new Intent(this, AppointmentViewActivity.class);
        startActivity(intent);
    }

    public void cancelClick(View view){
        Intent intent = new Intent(this, AppointmentViewActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_appointment:
                Toast.makeText(getApplicationContext(), "Appointment Deleted (NYI)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AppointmentsListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
