package com.rand.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class CustomerListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        Log.d("Tag1", "onCreate: CustomerListActivity");
        CustomerDBHandler dbHandler = new CustomerDBHandler(this, null, null, 2);
        Customer customer = new Customer("Joe Bob", "Bobson", "111 Super St.", "Syracuse", "NY", "13031", "Medium");
        dbHandler.addCustomer(customer);
        Toast.makeText(getApplicationContext(), dbHandler.findCustomer(customer.getId()).getFirstName(), Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_list_menu, menu);
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    public void exampleCustomerClick(View view){
        Intent intent = new Intent(this, CustomerViewActivity.class);
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
            case R.id.new_customer:
                intent = new Intent(this, CustomerEditActivity.class);
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
