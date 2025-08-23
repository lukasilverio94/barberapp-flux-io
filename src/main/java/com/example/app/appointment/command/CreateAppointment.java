package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentId;
import com.example.app.appointment.api.common.AppointmentStatus;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record CreateAppointment(
        AppointmentId appointmentId,
        @Valid @NotNull AppointmentDetailsRequest details) implements AppointmentUpdate {

    @Apply
    Appointment apply() {
        log.info("Method called ---> RegisterAppointment.apply() ========================");
        return Appointment.builder()
                .appointmentId(appointmentId)
                .details(details)
                .status(AppointmentStatus.requested)
                .build();
    }
}
