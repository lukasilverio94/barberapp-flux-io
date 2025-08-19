package com.example.app.appointment.query;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetails;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.handling.HandleQuery;
import io.fluxcapacitor.javaclient.tracking.handling.Request;
import jakarta.annotation.Nullable;

import java.util.List;

public record FindAppointments(@Nullable String term) implements Request<List<AppointmentDetails>> {

    @HandleQuery
    List<AppointmentDetails> handle() {
        return FluxCapacitor.search(Appointment.class).lookAhead(term).fetch(100);
    }
}
