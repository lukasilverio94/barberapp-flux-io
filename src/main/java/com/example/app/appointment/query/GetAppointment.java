package com.example.app.appointment.query;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentId;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.handling.HandleQuery;
import io.fluxcapacitor.javaclient.tracking.handling.Request;
import jakarta.validation.constraints.NotNull;

public record GetAppointment(@NotNull AppointmentId appointmentId) implements Request<Appointment> {

    @HandleQuery
    Appointment handleQuery() {
        var appointmentOptional = FluxCapacitor.getDocument(appointmentId, Appointment.class);
        return appointmentOptional.orElse(null);

        /*search(Appointment.class)
                .match(appointmentId, "appointmentId")
                .fetchFirstOrNull();*/
    }
}
