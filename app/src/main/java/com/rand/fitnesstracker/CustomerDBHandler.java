package com.rand.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;

public class CustomerDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "customerDB.db";
    private static final String TABLE_CUSTOMERS = "customers";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_ZIP = "zip";
    private static final String COLUMN_FITNESS_LEVEL = "fitness_level";

    public CustomerDBHandler(Context context, String name,
                             SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMERS_TABLE = "CREATE TABLE " +
                TABLE_CUSTOMERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_STATE + " TEXT, " +
                COLUMN_ZIP + " TEXT, " +
                COLUMN_FITNESS_LEVEL + " TEXT" + ")";
        db.execSQL(CREATE_CUSTOMERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        onCreate(db);
    }

    public int addCustomer(Customer customer){
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, customer.getFirstName());
        values.put(COLUMN_LAST_NAME, customer.getLastName());
        values.put(COLUMN_ADDRESS, customer.getAddress());
        values.put(COLUMN_CITY, customer.getCity());
        values.put(COLUMN_STATE, customer.getState());
        values.put(COLUMN_ZIP, customer.getZip());
        values.put(COLUMN_FITNESS_LEVEL, customer.getFitnessLevel());

        SQLiteDatabase db = this.getWritableDatabase();
        int returnValue = (int)db.insert(TABLE_CUSTOMERS, null, values);
        db.close();
        return returnValue;
    }

    public Customer findCustomer(int id){
        String query = "Select * FROM " +
                TABLE_CUSTOMERS +
                " WHERE " +
                COLUMN_ID +
                " = " +
                id;
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("TAG1", "findCustomer");
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = new Customer();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            customer.setId(Integer.parseInt(cursor.getString(0)));
            customer.setFirstName(cursor.getString(1));
            customer.setLastName(cursor.getString(2));
            customer.setAddress(cursor.getString(3));
            customer.setCity(cursor.getString(4));
            customer.setState(cursor.getString(5));
            customer.setZip(cursor.getString(6));
            customer.setFitnessLevel(cursor.getString(7));
            cursor.close();
        }else{
            customer = null;
        }
        db.close();
        return customer;
    }

    public boolean deleteCustomer(int customerID){
        boolean result = false;
        String query = "Select * FROM " +
                TABLE_CUSTOMERS +
                " WHERE " +
                COLUMN_ID +
                " = \"" +
                customerID +
                "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            db.delete(TABLE_CUSTOMERS, COLUMN_ID + " = ?", new String[] {cursor.getString(0)});
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public int countCustomers(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_CUSTOMERS);
    }

    public Customer[] getAllCustomers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Customer[] customers = new Customer[(int)DatabaseUtils.queryNumEntries(db, TABLE_CUSTOMERS)];
        String[] columns = {COLUMN_ID};
        Cursor cursor = db.query(TABLE_CUSTOMERS, columns,null, null, null, null, null);
        int index = 0;
        while(cursor.moveToNext()){
            customers[index] = (findCustomer(Integer.parseInt(cursor.getString(0))));
            index++;
        }
        return customers;
    }
}
