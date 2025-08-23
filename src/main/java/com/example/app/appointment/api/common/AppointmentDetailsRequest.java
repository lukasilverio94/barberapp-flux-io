package com.example.app.appointment.api.common;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentDetailsRequest(
        @NotNull String userId,
        @NotNull LocalDateTime dateTime,
        @NotNull AppointmentServiceType serviceType
) {
}
