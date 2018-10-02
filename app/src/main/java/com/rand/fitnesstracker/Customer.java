package com.rand.fitnesstracker;

import android.net.Uri;

import java.util.Comparator;

public class Customer {
    private int _id;
    private String _firstName;
    private String _address;
    private String _fitnessLevel;
    private String _lastName;
    private String _city;
    private String _zip;
    private String _state;
    private Uri _portraitLocation;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String address, String city, String state, String zip, String fitnessLevel, Uri portraitLocation) {
        _lastName = lastName;
        _city = city;
        _state = state;
        _zip = zip;
        _firstName = firstName;
        _address = address;
        _fitnessLevel = fitnessLevel;
        _portraitLocation = portraitLocation;
    }

    public int getId() {
        return _id;
    }

    Uri getPortraitLocation() {
        return _portraitLocation;
    }

    public void setId(int id) {
        _id = id;
    }

    String getFirstName() {
        return _firstName;
    }

    void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public String getAddress() {
        return _address;
    }

    public void setAddress(String address) {
        _address = address;
    }

    String getFitnessLevel() {
        return _fitnessLevel;
    }

    void setFitnessLevel(String fitnessLevel) {
        _fitnessLevel = fitnessLevel;
    }

    String getLastName() {
        return _lastName;
    }

    void setLastName(String lastName) {
        _lastName = lastName;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        _city = city;
    }

    public String getZip() {
        return _zip;
    }

    void setPortraitLocation(Uri portraitLocation) {
        _portraitLocation = portraitLocation;
    }

    public void setZip(String zip) {
        _zip = zip;
    }

    public String getState() {
        return _state;
    }

    public void setState(String state) {
        _state = state;
    }
}

class SortByNameCustomer implements Comparator<Customer> {

    @Override
    public int compare(Customer o1, Customer o2) {
        String name1 = o1.getFirstName() + " " + o1.getLastName();
        String name2 = o2.getFirstName() + " " + o2.getLastName();
        return name1.compareTo(name2);
    }
}
