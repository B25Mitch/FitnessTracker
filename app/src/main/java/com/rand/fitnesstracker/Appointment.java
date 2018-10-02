package com.rand.fitnesstracker;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;

public class Appointment {
    private int _id;
    private Timestamp _appointmentTime;
    private int _customerID;
    private String _location;

    Appointment() {
    }

    Appointment(Timestamp appointmentTime, int customerID, String location) {
        _appointmentTime = appointmentTime;
        _customerID = customerID;
        _location = location;
    }

    Date getAppointmentTime() {
        return _appointmentTime;
    }

    void setAppointmentTime(Timestamp appointmentTime) {
        _appointmentTime = appointmentTime;
    }

    int getCustomerID() {
        return _customerID;
    }

    void setCustomerID(int customerID) {
        _customerID = customerID;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
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
