package com.example.app.appointment.api.common;

import io.fluxcapacitor.javaclient.tracking.handling.IllegalCommandException;


public interface AppointmentErrors {

    IllegalCommandException alreadyExists = illegalCommandException("Appointment already exists"),
            notFound = illegalCommandException("Appointment not found"),
            outOfService = illegalCommandException("Barber not available at this time, check the schedule at homepage");


    public static IllegalCommandException illegalCommandException(String message) {
        return new IllegalCommandException(message);
    }


}
