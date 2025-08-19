package com.example.app.appointment.api.common;

import io.fluxcapacitor.javaclient.tracking.handling.IllegalCommandException;


public interface AppointmentErrors {

    IllegalCommandException alreadyExists = illegalCommandException("Appointment already exists"),
            notFound = illegalCommandException("Appointment not found"),
            outOfService = illegalCommandException("Opening time is 8:00 and closing time is 18:00. Closed at Sundays");


    static IllegalCommandException illegalCommandException(String message) {
        return new IllegalCommandException(message);
    }


}
