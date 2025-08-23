package com.example.app.appointment.api.common;

import io.fluxcapacitor.javaclient.modeling.Id;

public class AppointmentId extends Id<Appointment> {
    public AppointmentId(String id) {
        super(id);
    }

}
