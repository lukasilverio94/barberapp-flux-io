package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentId;
import com.example.app.appointment.api.common.AppointmentStatus;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import io.fluxcapacitor.javaclient.tracking.handling.Request;

public record RejectAppointment(AppointmentId appointmentId) implements AppointmentUpdate, Request<AppointmentId> {

    @Override
    public AppointmentDetailsRequest getRequestDetails(Appointment current) {
        return new AppointmentDetailsRequest(current.customerId(),
                current.barberId(),
                current.dateTime(),
                current.serviceType());
    }

    @Apply
    Appointment apply(Appointment appointment) {
        return new Appointment(
                appointment.appointmentId(),
                appointment.dateTime(),
                appointment.serviceType(),
                AppointmentStatus.cancelled,
                appointment.barberId(),
                appointment.customerId()
        );
    }
}
