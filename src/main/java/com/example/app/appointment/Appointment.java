package com.example.app.appointment;

import io.fluxcapacitor.javaclient.modeling.EntityId;
import lombok.Builder;

@Builder(toBuilder = true)
public record Appointment(
        @EntityId AppointmentId appointmentId,
        AppointmentStatus status,
        AppointmentDetailsRequest details
) {
}
