package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentId;
import com.example.app.appointment.api.common.AppointmentStatus;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import io.fluxcapacitor.javaclient.tracking.handling.Request;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RegisterAppointment(
        AppointmentId appointmentId,
        @Valid AppointmentDetailsRequest requestDetails) implements AppointmentCreate, Request<AppointmentId> {

    public RegisterAppointment(@NotNull @Valid AppointmentDetailsRequest requestDetails) {
        this(FluxCapacitor.generateId(AppointmentId.class), requestDetails);
        System.out.println("Constructor called --> RegisterAppointment ========================");
    }

    @Apply
    Appointment apply() {
        System.out.println("Method called ---> RegisterAppointment.apply() ========================");
        return Appointment.builder()
                .appointmentId(appointmentId)
                .barberId(requestDetails.barberId())
                .customerId(requestDetails.userId())
                .dateTime(requestDetails.dateTime())
                .status(AppointmentStatus.requested)
                .serviceType(requestDetails.serviceType())
                .build();
    }

    @Override
    public AppointmentDetailsRequest getRequestDetails(Appointment current) {
        return this.requestDetails;
    }
}
