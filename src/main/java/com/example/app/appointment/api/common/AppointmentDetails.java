package com.example.app.appointment.api.common;

import com.example.app.user.UserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AppointmentDetails(
        String barberId,
        String customerId,
        LocalDate date,
        LocalTime startTime,
        AppointmentServiceType serviceType
) {

}
