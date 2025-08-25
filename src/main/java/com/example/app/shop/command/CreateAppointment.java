package com.example.app.shop.command;

import com.example.app.appointment.*;
import com.example.app.shop.ShopErrors;
import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static io.fluxcapacitor.javaclient.modeling.AssertLegal.HIGHEST_PRIORITY;

@Slf4j
public record CreateAppointment(
        ShopId shopId,
        AppointmentId appointmentId,
        AppointmentDetailsRequest details) implements ShopCommand {

    @AssertLegal(priority = HIGHEST_PRIORITY)
    void assertShopExists(@Nullable Shop current) {
        if (current == null) {
            throw ShopErrors.notFound;
        }
    }

    @AssertLegal
    void assertIsNotInPast(@Null Shop current) {
        LocalDateTime requestedDateTime = details.dateTime();

        if (requestedDateTime.isBefore(LocalDateTime.now())) {
            throw AppointmentErrors.invalidDatetime;
        }
    }

    @AssertLegal
    void assertIsWithinBusinessHours(@Nullable Shop current) {
        if (current.appointments() == null) {
            return;
        }

        LocalDateTime requestedStartTime= details.dateTime();
        LocalDateTime requestedEndTime = requestedStartTime.plusMinutes(30);

        var opening = current.details().openingTime();
        var closing = current.details().closingTime();

        if (requestedStartTime.toLocalTime().isBefore(opening) || requestedEndTime.toLocalTime().isAfter(closing)) {
            throw ShopErrors.outOfService;
        }
    }

    @AssertLegal
    void assertTimeslotAvailable(@Nullable Shop current)  {
        if ( current.appointments() == null) {
            return;
        }

        LocalDateTime requestedStartTime = details.dateTime();
        LocalDateTime requestedEndtime = requestedStartTime.plusMinutes(30);

        for (Appointment appt: current.appointments()) {
            if (appt.status() != AppointmentStatus.accepted) {
                continue;
            }
            log.info("Checking overlap, existing appointments: {}", current.appointments());

            LocalDateTime existingStart = appt.details().dateTime();
            LocalDateTime existingEnd = existingStart.plusMinutes(30);

            if (requestedStartTime.isBefore(existingEnd) && requestedEndtime.isAfter(existingStart)) {
                var exc = AppointmentErrors.timeslotUnavailable;
                log.error("User tried to create appointment but the timeslot is not available", exc);
                throw exc;
            }
        }
    }

    @Apply
    Appointment apply() {
        return Appointment.builder()
                .appointmentId(appointmentId)
                .details(details)
                .status(AppointmentStatus.requested)
                .build();
    }
}
