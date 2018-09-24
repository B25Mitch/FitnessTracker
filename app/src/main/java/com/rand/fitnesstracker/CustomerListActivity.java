package com.rand.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;

import static android.widget.Toast.LENGTH_SHORT;

public class CustomerListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        LinearLayout linearLayout = findViewById(R.id.customer_linear_layout);
        CustomerDBHandler dbHandler = new CustomerDBHandler(this, null, null, 2);
        Customer[] customers = dbHandler.getAllCustomers();
        Arrays.sort(customers, new SortByNameCustomer());
        for (Customer customer : customers) {
            Button button = new Button(this);
            button.setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
            final int CUSTOMER_ID = customer.getId();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CustomerListActivity.this, CustomerViewActivity.class);
                    intent.putExtra("CUSTOMER_ID", CUSTOMER_ID);
                    startActivity(intent);
                }
            });
            linearLayout.addView(button);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_list_menu, menu);
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log_off:
                Toast.makeText(getApplicationContext(), "Logging Off", LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.new_customer:
                intent = new Intent(this, CustomerEditActivity.class);
                intent.putExtra("CUSTOMER_ID", -1);
                startActivity(intent);
                return true;
            case R.id.view_appointments:
                intent = new Intent(this, AppointmentsListActivity.class);
                intent.putExtra("CUSTOMER_ID", -1);
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
