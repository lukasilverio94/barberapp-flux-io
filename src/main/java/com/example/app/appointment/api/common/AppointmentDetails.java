package com.example.app.appointment.api.common;

import java.time.LocalDateTime;


public record AppointmentDetails(
        String barberId,
        String customerId,
        LocalDateTime dateTime,
        AppointmentServiceType serviceType
) {

}
