package com.example.app.appointment.api.common;

import com.example.app.user.UserId;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentDetailsRequest(
        @NotNull(message = "User ID is required") UserId userId,
        @NotNull(message = "Barber ID is required") UserId barberId,
        @NotNull(message = "A day is required") LocalDate date,
        @NotNull(message = "Start time is required") LocalTime startTime,
        @NotNull(message = "Service Type should be 'beard' or 'haircut'") AppointmentServiceType serviceType
) {
}
