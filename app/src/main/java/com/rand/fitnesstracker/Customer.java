package com.rand.fitnesstracker;

public class Customer {
    private static int count = 0;
    private int _id;
    private String _firstName;
    private String _address;
    private String _fitnessLevel;
    private String _lastName;
    private String _city;
    private String _zip;
    private String _state;

    public Customer(){
    }

    public Customer(String firstName, String lastName, String address, String city, String state, String zip, String fitnessLevel) {
        _lastName = lastName;
        _city = city;
        _state = state;
        _zip = zip;
        _id = ++count;
        _firstName = firstName;
        _address = address;
        _fitnessLevel = fitnessLevel;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id){
        _id = id;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public String getAddress() {
        return _address;
    }

    public void setAddress(String address) {
        _address = address;
    }

    public String getFitnessLevel() {
        return _fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        _fitnessLevel = fitnessLevel;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
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