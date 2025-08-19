package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentErrors;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.tracking.TrackSelf;
import io.fluxcapacitor.javaclient.tracking.handling.HandleCommand;
import jakarta.annotation.Nullable;

@TrackSelf
public interface AppointmentUpdate extends AppointmentCommand {

    @HandleCommand
    default Appointment handle() {
        System.out.println("Method called ---> AppointmentUpdate.handle() ========================");
        var createdAppointment = FluxCapacitor.loadAggregate(appointmentId()).assertAndApply(this);
        return createdAppointment.get();
    }

    @AssertLegal
    default void assertExistence(@Nullable Appointment current) {
        System.out.println("Method called ---> AppointmentUpdate.assertExistence() ========================");
        if (current == null) {
            throw AppointmentErrors.notFound;
        }
    }
}
