package com.rand.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CommonFunctions extends AppCompatActivity{
    public void logOff(CustomerViewActivity that){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
