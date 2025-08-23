package com.example.app.appointment.command;

import com.example.app.appointment.api.common.AppointmentId;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.Consumer;
import io.fluxcapacitor.javaclient.tracking.TrackSelf;
import io.fluxcapacitor.javaclient.tracking.handling.HandleCommand;
import jakarta.validation.constraints.NotNull;

@TrackSelf
@Consumer(name = "appointment-updates")
public interface AppointmentUpdate{

    @NotNull AppointmentId appointmentId();

    @HandleCommand
    default void handle() {
        FluxCapacitor.loadAggregate(appointmentId()).assertAndApply(this);
    }
}
