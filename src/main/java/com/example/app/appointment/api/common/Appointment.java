package com.example.app.appointment.api.common;

import io.fluxcapacitor.common.search.Facet;
import io.fluxcapacitor.javaclient.modeling.Aggregate;
import io.fluxcapacitor.javaclient.modeling.EntityId;
import lombok.Builder;

import java.time.LocalDateTime;

@Aggregate(searchable = true, collection ="appointment")
@Builder(toBuilder = true)
public record Appointment(
        @EntityId AppointmentId appointmentId,
        LocalDateTime dateTime,
        AppointmentServiceType serviceType,
        AppointmentStatus status,
        @Facet String barberId,
        @Facet String customerId
) {


}
