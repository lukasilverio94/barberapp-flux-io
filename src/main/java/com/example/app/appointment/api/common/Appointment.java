package com.example.app.appointment.api.common;

import io.fluxcapacitor.javaclient.modeling.EntityId;
import lombok.Builder;

@Builder(toBuilder = true)
public record Appointment(
        @EntityId AppointmentId appointmentId,
        AppointmentDetailsRequest details,
        AppointmentStatus status
) {
}
