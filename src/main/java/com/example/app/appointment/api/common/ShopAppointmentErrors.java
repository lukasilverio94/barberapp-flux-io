package com.example.app.appointment.api.common;

import io.fluxcapacitor.javaclient.tracking.handling.IllegalCommandException;


public interface ShopAppointmentErrors {

    IllegalCommandException alreadyExists = illegalCommandException("Appointment already exists"),
            notFound = illegalCommandException("Shop not found"),
            invalidDatetime = illegalCommandException("Appointment can't be on the past. Try a valid date and time"),
            outOfService = illegalCommandException("Barbershop is closed at this time. Check the availability"),
            timeslotUnavailable =  illegalCommandException("Timeslot not avaialble");


    static IllegalCommandException illegalCommandException(String message) {
        return new IllegalCommandException(message);
    }


}
