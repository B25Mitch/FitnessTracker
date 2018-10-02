package com.rand.fitnesstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomerEditActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;

    private int customerID;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit);
        Bundle extras = getIntent().getExtras();

        customerID = 0;
        if (extras != null) {
            customerID = extras.getInt("CUSTOMER_ID");
        }
        if (customerID != -1) {
            CustomerDBHandler dbHandler = new CustomerDBHandler(this, null, null, 1);
            Customer customer = dbHandler.findCustomer(customerID);
            EditText editFirstName = findViewById(R.id.edit_first_name);
            editFirstName.setText(customer.getFirstName());
            EditText editLastName = findViewById(R.id.edit_last_name);
            editLastName.setText(customer.getLastName());
            EditText editAddress = findViewById(R.id.edit_address);
            editAddress.setText(customer.getAddress());
            EditText editState = findViewById(R.id.edit_state);
            editState.setText(customer.getState());
            EditText editCity = findViewById(R.id.edit_city);
            editCity.setText(customer.getCity());
            EditText editZip = findViewById(R.id.edit_zip);
            editZip.setText(customer.getZip());
            EditText editFitness = findViewById(R.id.edit_fitness);
            editFitness.setText(customer.getFitnessLevel());
            if (!customer.getPortraitLocation().toString().equals("")) {
                ImageView portraitView = findViewById(R.id.edit_customer_portrait);
                portraitView.setImageURI(customer.getPortraitLocation());
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_edit_menu, menu);
        return true;
    }

    @SuppressWarnings("unused")
    public void okClick(View view) {
        CustomerDBHandler dbHandler = new CustomerDBHandler(this, null, null, 1);
        EditText editFirstName = findViewById(R.id.edit_first_name);
        String firstName = editFirstName.getText().toString();
        EditText editLastName = findViewById(R.id.edit_last_name);
        String lastName = editLastName.getText().toString();
        EditText editAddress = findViewById(R.id.edit_address);
        String address = editAddress.getText().toString();
        EditText editState = findViewById(R.id.edit_state);
        String state = editState.getText().toString();
        EditText editCity = findViewById(R.id.edit_city);
        String city = editCity.getText().toString();
        EditText editZip = findViewById(R.id.edit_zip);
        String zip = editZip.getText().toString();
        EditText editFitness = findViewById(R.id.edit_fitness);
        String fitness = editFitness.getText().toString();
        Uri portraitLocation = Uri.parse("");
        ImageView portraitView = findViewById(R.id.edit_customer_portrait);
        if (portraitView.getTag() != null) {
            portraitLocation = Uri.parse(portraitView.getTag().toString());
        }

        Customer customer = new Customer(firstName, lastName, address, city, state, zip, fitness, portraitLocation);

        int newID = customerID;
        if (customerID != -1) {
            customer.setId(customerID);
            dbHandler.modifyCustomer(customer);
        } else {
            newID = dbHandler.addCustomer(customer);
        }
        Intent intent = new Intent(this, CustomerViewActivity.class);
        intent.putExtra("CUSTOMER_ID", newID);
        startActivity(intent);
    }

    @SuppressWarnings("unused")
    public void cancelClick(View view) {
        if (customerID != -1) {
            Intent intent = new Intent(this, CustomerViewActivity.class);
            intent.putExtra("CUSTOMER_ID", customerID);
            startActivity(intent);
        }
        Intent intent = new Intent(this, CustomerListActivity.class);
        startActivity(intent);
    }

    public void photoClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.rand.fitnesstracker",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = findViewById(R.id.edit_customer_portrait);
        imageView.setTag(photoURI.toString());
        imageView.setImageURI(photoURI);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_customer:
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CustomerEditActivity.this);
                myAlertDialog.setTitle("Delete");
                myAlertDialog.setMessage("Are you sure you want to delete?");
                myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        CustomerDBHandler customerDBHandler = new CustomerDBHandler(CustomerEditActivity.this, null, null, 1);
                        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler(CustomerEditActivity.this, null, null, 1);
                        Appointment[] appointments = appointmentDBHandler.getAllAppointments(customerID);
                        for (Appointment appointment : appointments) {
                            appointmentDBHandler.deleteAppointment(appointment.getId());
                        }
                        customerDBHandler.deleteCustomer(customerID);
                        Intent intent = new Intent(CustomerEditActivity.this, CustomerListActivity.class);
                        startActivity(intent);
                    }
                });
                myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        // do nothing
                    }
                });
                myAlertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
