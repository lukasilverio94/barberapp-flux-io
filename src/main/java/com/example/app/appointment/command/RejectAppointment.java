package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentId;
import com.example.app.appointment.api.common.AppointmentStatus;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import io.fluxcapacitor.javaclient.tracking.handling.Request;

public record RejectAppointment(AppointmentId appointmentId) implements AppointmentUpdate, Request<AppointmentId> {

    @Apply
    Appointment apply(Appointment appointment) {
        return new Appointment(
                appointment.appointmentId(),
                appointment.details(),
                AppointmentStatus.cancelled
        );
    }
}
