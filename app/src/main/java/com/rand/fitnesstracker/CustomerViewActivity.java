package com.rand.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CustomerViewActivity extends AppCompatActivity {

    int customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        Bundle extras = getIntent().getExtras();

        customerID = 0;
        if (extras != null) {
            customerID = extras.getInt("CUSTOMER_ID");
        }
        Log.d("TAG!", "" + customerID);
        CustomerDBHandler dbHandler = new CustomerDBHandler(this, null, null, 1);
        Customer customer = dbHandler.findCustomer(customerID);
        TextView viewName = (TextView) findViewById(R.id.view_name);
        viewName.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
        TextView viewAddress = (TextView) findViewById(R.id.view_address);
        viewAddress.setText(customer.getAddress());
        TextView viewCity = (TextView) findViewById(R.id.view_city);
        viewCity.setText(customer.getCity());
        TextView viewState = (TextView) findViewById(R.id.view_state);
        viewState.setText(customer.getState());
        TextView viewZip = (TextView) findViewById(R.id.view_zip);
        viewZip.setText(customer.getZip());
        TextView viewFitness = (TextView) findViewById(R.id.view_fitness);
        viewFitness.setText(customer.getFitnessLevel());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    public void editCustomerClick(View view){
        Intent intent = new Intent(this, CustomerEditActivity.class);
        intent.putExtra("CUSTOMER_ID", customerID);
        startActivity(intent);
    }

    public void viewAppointmentsClick(View view){
        Intent intent = new Intent(this, CustomerAppointmentsActivity.class);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
