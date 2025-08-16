package com.example.app.appointment.api.common;

import io.fluxcapacitor.javaclient.modeling.Aggregate;
import io.fluxcapacitor.javaclient.modeling.EntityId;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Aggregate(searchable = true, timestampPath = "")
@Builder(toBuilder = true)
public record Appointment(
        @EntityId AppointmentId appointmentId,
        LocalDate date,
        LocalTime startTime,
        AppointmentServiceType serviceType,
        String barberId,
        String customerId
) {
}
