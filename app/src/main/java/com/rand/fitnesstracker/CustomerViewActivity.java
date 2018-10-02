package com.rand.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerViewActivity extends AppCompatActivity {

    private int customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        Bundle extras = getIntent().getExtras();

        customerID = -1;
        if (extras != null) {
            customerID = extras.getInt("CUSTOMER_ID");
        }
        CustomerDBHandler dbHandler = new CustomerDBHandler(this, null, null, 1);
        Customer customer = dbHandler.findCustomer(customerID);
        TextView viewName = findViewById(R.id.view_name);
        viewName.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
        TextView viewAddress = findViewById(R.id.view_address);
        viewAddress.setText(customer.getAddress());
        TextView viewCity = findViewById(R.id.view_city);
        viewCity.setText(customer.getCity());
        TextView viewState = findViewById(R.id.view_state);
        viewState.setText(customer.getState());
        TextView viewZip = findViewById(R.id.view_zip);
        viewZip.setText(customer.getZip());
        TextView viewFitness = findViewById(R.id.view_fitness);
        viewFitness.setText(customer.getFitnessLevel());
        if (!customer.getPortraitLocation().toString().equals("")) {
            ImageView imageView = findViewById(R.id.view_customer_portrait);
            imageView.setImageURI(customer.getPortraitLocation());
        }
    }

    @SuppressWarnings("unused")
    public void editCustomerClick(View view) {
        Intent intent = new Intent(this, CustomerEditActivity.class);
        intent.putExtra("CUSTOMER_ID", customerID);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.common_menu, menu);
        return true;
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

    @SuppressWarnings("unused")
    public void viewAppointmentsClick(View view) {
        Intent intent = new Intent(this, AppointmentsListActivity.class);
        intent.putExtra("CUSTOMER_ID", customerID);
        startActivity(intent);
    }
}
