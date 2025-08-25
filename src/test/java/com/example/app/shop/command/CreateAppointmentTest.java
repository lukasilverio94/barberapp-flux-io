package com.example.app.shop.command;

import com.example.app.appointment.AppointmentErrors;
import com.example.app.util.BaseTest;
import org.junit.jupiter.api.Test;

public class CreateAppointmentTest extends BaseTest {

    @Test
    void createShopAppointment() {
        fixture.givenCommands("/shop/create-shop.json")
                .whenCommand("/appointment/create-appointment.json")
                .expectEvents("/appointment/create-appointment.json");
    }

    @Test
    void createAppointmentOnPastDateThrowsException() {
        fixture.givenCommands("/shop/create-shop.json")
                .whenCommand("/appointment/create-invalid-appointment.json")
                .expectExceptionalResult(AppointmentErrors.invalidDatetime);
    }

    @Test
    void createAppointmentTimeslotOverlapsThrowsException() {
        fixture.givenCommands("/shop/create-shop.json", "/appointment/create-appointment.json", "/appointment/accept-appointment.json")
                .whenCommand("/appointment/create-overlapping-appointment.json")
                .expectExceptionalResult(AppointmentErrors.timeslotUnavailable);
    }
}
