package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentErrors;
import com.example.app.appointment.api.common.AppointmentId;
import io.fluxcapacitor.common.api.search.constraints.BetweenConstraint;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.publishing.routing.RoutingKey;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.concurrent.ExecutionException;

public interface AppointmentCommand {

    LocalTime OPENING_TIME = LocalTime.of(8, 0);
    LocalTime CLOSING_TIME = LocalTime.of(20, 0);
    int APPOINTMENT_DURATION_IN_MINUTES = 30;


    @NotNull
    @RoutingKey
    AppointmentId appointmentId();

    @NotNull
    AppointmentDetailsRequest getRequestDetails(Appointment current);

    @AssertLegal
    default void assertIsWithinBusinessHours(@Nullable Appointment current) {
        System.out.println("Method called --> AppointmentCommand.assertIsWithinBusinessHours() ========================");
        var requestDetails = this.getRequestDetails(current);
        var isShopClosed = requestDetails.dateTime().toLocalTime().isBefore(OPENING_TIME) || requestDetails.dateTime().toLocalTime().plusMinutes(APPOINTMENT_DURATION_IN_MINUTES).isAfter(CLOSING_TIME);
        var isSunday = requestDetails.dateTime().getDayOfWeek() == DayOfWeek.SUNDAY;
        if (isSunday || isShopClosed) {
            throw AppointmentErrors.outOfService;
        }
    }

    /*
    If needs to reference the request body, you have to create the method here, from what I can see.
    If it’s a validation only against the current state of the application before the request, i can put it
    in the interface as a default method.
    This one specifically would just need to check if the query is finding something and add another match
    to only fetch appointments for this barber.
     */
    @AssertLegal
    default void assertBarberIsAvailable(@Nullable Appointment current) throws ExecutionException, InterruptedException {
        System.out.println("Method called --> AppointmentCommand.assertBarberIsAvailable() ========================");
        var requestDetails = this.getRequestDetails(current);
        var dateTime = requestDetails.dateTime();
        var searchResult = FluxCapacitor
                .search(Appointment.class)
                .query(requestDetails.barberId(), "barberId")
                .constraint(BetweenConstraint.between(dateTime, dateTime.plusMinutes(APPOINTMENT_DURATION_IN_MINUTES), "dateTime"))
                .fetchAll();

        if (!searchResult.isEmpty()) {
            throw AppointmentErrors.alreadyBookedTimeslot;
        }
    }


}
