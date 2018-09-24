package com.rand.fitnesstracker;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;

public class Appointment {
    private int _id;
    private Timestamp _appointmentTime;
    private int _customerID;
    private String _location;

    public Appointment() {
    }

    public Appointment(Timestamp appointmentTime, int customerID, String location) {
        _appointmentTime = appointmentTime;
        _customerID = customerID;
        _location = location;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public Date getAppointmentTime() {
        return _appointmentTime;
    }

    public void setAppointmentTime(Timestamp appointmentTime) {
        _appointmentTime = appointmentTime;
    }

    public int getCustomerID() {
        return _customerID;
    }

    public void setCustomerID(int customerID) {
        _customerID = customerID;
    }

    public String getLocation() {
        return _location;
    }

    public void setLocation(String location) {
        _location = location;
    }

}

class SortByNameAppointment implements Comparator<Appointment> {

    @Override
    public int compare(Appointment o1, Appointment o2) {
        return o1.getAppointmentTime().compareTo(o2.getAppointmentTime());
    }
}
