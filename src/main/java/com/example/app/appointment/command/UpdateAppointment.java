package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentId;
import com.example.app.appointment.api.common.AppointmentServiceType;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import io.fluxcapacitor.javaclient.tracking.handling.Request;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record UpdateAppointment(AppointmentId appointmentId,
                                @Valid AppointmentDetailsRequest requestDetails) implements AppointmentUpdate, Request<AppointmentId> {

    public UpdateAppointment(@NotNull @Valid AppointmentDetailsRequest requestDetails) {
        this(FluxCapacitor.generateId(AppointmentId.class), requestDetails);
        System.out.println("Constructor called: UpdateAppointment ========================");
    }


    @Apply
    Appointment apply(Appointment appointment) {
        System.out.println("Method called: UpdateAppointment.apply() ========================");
        return Appointment.builder()
                .appointmentId(appointmentId)
                .barberId(requestDetails.barberId())
                .customerId(requestDetails.userId())
                .dateTime(requestDetails.dateTime())
                .serviceType(mapToServiceType(requestDetails.serviceType()))
                .build();
    }

    private AppointmentServiceType mapToServiceType(AppointmentServiceType type) {
        return AppointmentServiceType.valueOf(type.name());
    }


    @Override
    public AppointmentDetailsRequest getRequestDetails(Appointment current) {
        return this.requestDetails;
    }
}
