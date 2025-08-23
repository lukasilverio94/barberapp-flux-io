package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentId;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import jakarta.validation.constraints.NotNull;

public record ApproveAppointment(
        /*BarbershopId barbershopId,*/ @NotNull AppointmentId appointmentId) implements AppointmentUpdate {

    @Apply
    Appointment apply(Appointment appointment) {
        return appointment.toBuilder().build();
    }
}
