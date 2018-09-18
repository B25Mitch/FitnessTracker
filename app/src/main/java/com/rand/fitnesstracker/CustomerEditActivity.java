package com.rand.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerEditActivity extends AppCompatActivity {

    int customerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit);
        Bundle extras = getIntent().getExtras();

        customerID = 0;
        if (extras != null) {
            customerID = extras.getInt("CUSTOMER_ID");
        }
        if(customerID != -1) {
            CustomerDBHandler dbHandler = new CustomerDBHandler(this, null, null, 1);
            Customer customer = dbHandler.findCustomer(customerID);
            EditText editFirstName = (EditText) findViewById(R.id.edit_first_name);
            editFirstName.setText(customer.getFirstName());
            EditText editLastName = (EditText) findViewById(R.id.edit_last_name);
            editLastName.setText(customer.getLastName());
            EditText editAddress = (EditText) findViewById(R.id.edit_address);
            editAddress.setText(customer.getAddress());
            EditText editState = (EditText) findViewById(R.id.edit_state);
            editState.setText(customer.getState());
            EditText editCity = (EditText) findViewById(R.id.edit_city);
            editCity.setText(customer.getCity());
            EditText editZip = (EditText) findViewById(R.id.edit_zip);
            editZip.setText(customer.getZip());
            EditText editFitness = (EditText) findViewById(R.id.edit_fitness);
            editFitness.setText(customer.getFitnessLevel());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_edit_menu, menu);
        return true;
    }

    public void okClick(View view){
        CustomerDBHandler dbHandler = new CustomerDBHandler(this, null, null, 1);
        if (customerID != -1) {
            Customer customer = dbHandler.findCustomer(customerID);
            dbHandler.deleteCustomer(customerID);
        }
        EditText editFirstName = (EditText) findViewById(R.id.edit_first_name);
        String firstName = editFirstName.getText().toString();
        EditText editLastName = (EditText) findViewById(R.id.edit_last_name);
        String lastName = editLastName.getText().toString();
        EditText editAddress = (EditText) findViewById(R.id.edit_address);
        String address = editAddress.getText().toString();
        EditText editState = (EditText) findViewById(R.id.edit_state);
        String state = editState.getText().toString();
        EditText editCity = (EditText) findViewById(R.id.edit_city);
        String city = editCity.getText().toString();
        EditText editZip = (EditText) findViewById(R.id.edit_zip);
        String zip = editZip.getText().toString();
        EditText editFitness = (EditText) findViewById(R.id.edit_fitness);
        String fitness = editFitness.getText().toString();


        int newID = dbHandler.addCustomer(new Customer(firstName, lastName, address, city, state, zip, fitness));
        Intent intent = new Intent(this, CustomerViewActivity.class);
        intent.putExtra("CUSTOMER_ID", newID);
        startActivity(intent);
    }

    public void cancelClick(View view){
        if(customerID != -1) {
            Intent intent = new Intent(this, CustomerViewActivity.class);
            intent.putExtra("CUSTOMER_ID", customerID);
            startActivity(intent);
        }
        Intent intent = new Intent(this, CustomerListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_customer:
                Toast.makeText(getApplicationContext(), "Customer Deleted (NYI)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CustomerListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
