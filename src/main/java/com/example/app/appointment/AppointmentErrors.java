package com.example.app.appointment;

import io.fluxcapacitor.javaclient.tracking.handling.IllegalCommandException;


public interface AppointmentErrors {

    IllegalCommandException alreadyExists = illegalCommandException("Appointment already exists"),
            notFound = illegalCommandException("Appointment not found"),
            invalidDatetime = illegalCommandException("Appointment can't be on the past. Try a valid date and time"),
            timeslotUnavailable =  illegalCommandException("Timeslot not available"),
            genericError = illegalCommandException("An unknown error happened during the appointment operation.");


    static IllegalCommandException illegalCommandException(String message) {
        return new IllegalCommandException(message);
    }


}
