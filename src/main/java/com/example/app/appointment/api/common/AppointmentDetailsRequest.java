package com.example.app.appointment.api.common;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentDetailsRequest(
        @NotNull(message = "User ID is required") String userId,
        @NotNull(message = "Barber ID is required") String barberId,
        @NotNull(message = "A day is required") LocalDateTime dateTime,
        @NotNull(message = "Service Type should be 'beard' or 'haircut'") AppointmentServiceType serviceType
) {
}
