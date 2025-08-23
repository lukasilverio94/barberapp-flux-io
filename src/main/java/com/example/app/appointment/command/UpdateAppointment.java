package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentId;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import io.fluxcapacitor.javaclient.tracking.handling.IllegalCommandException;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateAppointment(AppointmentId appointmentId,
                                @Valid @NotNull
                                AppointmentDetailsRequest details) implements AppointmentUpdate {
    @AssertLegal
    void assertProjectExists(@Nullable Appointment appointment) {
        if (appointment == null) {
            throw new IllegalCommandException("This appointment does not exist.");
        }
    }

    @Apply
    Appointment apply(Appointment appointment) {
        return appointment.toBuilder().details(details).build();
    }

}

