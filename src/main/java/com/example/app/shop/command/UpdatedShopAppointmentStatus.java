package com.example.app.shop.command;

import com.example.app.appointment.Appointment;
import com.example.app.appointment.AppointmentErrors;
import com.example.app.appointment.AppointmentId;
import com.example.app.appointment.AppointmentStatus;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import jakarta.validation.constraints.NotNull;

public record UpdatedShopAppointmentStatus(
        ShopId shopId,
        @NotNull AppointmentId appointmentId,
        AppointmentStatus appointmentStatus
) implements ShopCommand{

    @AssertLegal
    void assertAppointmentExists(Appointment appointment) {

        if(appointment == null) {
            throw AppointmentErrors.notFound;
        }
    }

    @Apply
    Appointment apply(Appointment current) {
        return current.toBuilder()
                .status(appointmentStatus)
                .build();
    }
}
