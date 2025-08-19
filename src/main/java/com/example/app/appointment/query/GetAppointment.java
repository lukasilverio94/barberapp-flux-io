package com.example.app.appointment.query;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetails;
import com.example.app.appointment.api.common.AppointmentErrors;
import com.example.app.appointment.api.common.AppointmentId;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.handling.HandleQuery;
import io.fluxcapacitor.javaclient.tracking.handling.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GetAppointment(
        @NotNull AppointmentId appointmentId
) implements Request<AppointmentDetails> {

    public GetAppointment(@NotBlank String appointmentId) {
        this(new AppointmentId(appointmentId));
    }

    @HandleQuery
    AppointmentDetails handle() {
        return FluxCapacitor.getDocument(appointmentId, Appointment.class)
                .map(entity -> new AppointmentDetails(entity.barberId(),
                        entity.customerId(),
                        entity.dateTime(),
                        entity.serviceType()))
                .orElseThrow(() -> AppointmentErrors.notFound);
    }

}
