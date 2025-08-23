package com.example.app.shop.command;

import com.example.app.appointment.api.common.*;
import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public record CreateShopAppointment(
        ShopId shopId,
        AppointmentId appointmentId,
        AppointmentDetailsRequest details) implements ShopCommand {

    @AssertLegal
    void assertShopExists(@Null Shop shop) {
        log.info("===========================Entrou no método CreateShopAppointment.assertShopExists()===========================");

        if (shop == null) {
            throw ShopAppointmentErrors.notFound;
        }
    }

    @AssertLegal
    void assertIsNotInPast(@Null Shop current) {
        log.info("===========================Entrou no método CreateShopAppointment.assertIsNotINPast()===========================");
        LocalDateTime requestedDateTime = details.dateTime();

        if (requestedDateTime.isBefore(LocalDateTime.now())) {
            throw ShopAppointmentErrors.invalidDatetime;
        }
    }

    @AssertLegal
    void assertIsWithinBusinessHours(@Nullable Shop current) {
        log.info("===========================Entrou no método CreateShopAppointment.assertIsWithinBusinessHours()===========================");
        if (current == null || current.appointments() == null) {
            return;
        }

        LocalDateTime requestedStartTime= details.dateTime();
        LocalDateTime requestedEndTime = requestedStartTime.plusMinutes(30);

        var opening = current.details().openingTime();
        var closing = current.details().closingTime();

        if (requestedStartTime.toLocalTime().isBefore(opening) || requestedEndTime.toLocalTime().isAfter(closing)) {
            throw ShopAppointmentErrors.outOfService;
        }
    }

    @AssertLegal
    void assertTimeslotAvailable(@Nullable Shop current)  {
        log.info("===========================Entrou no método CreateShopAppointment.assertTimeslotAvailable()===========================");

        if (current == null || current.appointments() == null) {
            return;
        }

        LocalDateTime requestedStartTime = details.dateTime();
        LocalDateTime requestedEndtime = requestedStartTime.plusMinutes(30);

        for (Appointment appt: current.appointments()) {
            if (appt == null || appt.details() == null || appt.details().dateTime() == null) {
                continue;
            }
            LocalDateTime existingStart = appt.details().dateTime();
            LocalDateTime existingEnd = existingStart.plusMinutes(30);

            if (requestedStartTime.isBefore(existingEnd) && requestedEndtime.isAfter(existingStart)) {
                throw ShopAppointmentErrors.timeslotUnavailable;
            }
        }
    }

    @Apply
    Appointment apply() {
        log.info("===========================Entrou no método CreateShopAppointment.apply()===========================");
        return Appointment.builder()
                .appointmentId(appointmentId)
                .details(details)
                .status(AppointmentStatus.requested)
                .build();
    }
}
