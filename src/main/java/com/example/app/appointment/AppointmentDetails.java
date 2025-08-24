package com.example.app.appointment;

import java.time.LocalDateTime;


public record AppointmentDetails(
        String barberId,
        String customerId,
        LocalDateTime dateTime,
        AppointmentServiceType serviceType,
        AppointmentStatus status
) {

}
