package com.example.app.shop.command;

import com.example.app.appointment.AppointmentErrors;
import com.example.app.appointment.AppointmentStatus;
import com.example.app.shop.ShopErrors;
import com.example.app.shop.model.Shop;
import com.example.app.util.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class UpdatedShopAppointmentStatusTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("appointmentStatuses")
    void updateStatusNonExistingShopThrowsException(AppointmentStatus resultStatus, String fileName) {
        fixture.whenCommand(fileName)
                .expectExceptionalResult(ShopErrors.notFound);
    }

    @ParameterizedTest
    @MethodSource("appointmentStatuses")
    void changeAppointmentStatusSuccessfully(AppointmentStatus resultStatus, String fileName) {
        fixture.givenCommands("/shop/create-shop.json", "/appointment/create-appointment.json")
                .whenCommand(fileName)
                .<Shop>expectResult(result -> result.appointments().stream()
                        .filter(appointment -> appointment.appointmentId().toString().equals("a1"))
                        .anyMatch(appointment -> appointment.status() == resultStatus)
                );
    }

    @ParameterizedTest
    @MethodSource("appointmentStatuses")
    void changeAppointmentStatusNonExistingAppointment(AppointmentStatus resultStatus, String fileName) {
        fixture.givenCommands("/shop/create-shop.json")
                .whenCommand(fileName)
                .expectExceptionalResult(AppointmentErrors.notFound);
    }

    private static Stream<Arguments> appointmentStatuses() {
        return Stream.of(
                Arguments.of(AppointmentStatus.accepted, "/appointment/accept-appointment.json"),
                Arguments.of(AppointmentStatus.cancelled, "/appointment/cancel-appointment.json")
        );
    }
}
