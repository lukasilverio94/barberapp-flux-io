package com.example.app.appointment;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetails;
import com.example.app.appointment.query.FindAppointments;
import com.example.app.appointment.query.GetAppointment;
import io.fluxcapacitor.javaclient.test.TestFixture;
import io.fluxcapacitor.javaclient.tracking.handling.validation.ValidationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AppointmentTest {

    private final TestFixture testFixture = TestFixture.create();

    @Test
    void registerAppointment() {
        testFixture.whenCommand("/appointment/register-appointment.json")
                .expectEvents("/appointment/register-appointment.json");
    }

    @Test
    void testRegisterInvalidAppointment() {
        testFixture.whenCommand("/appointment/register-invalid-appointment.json")
                .expectExceptionalResult(ValidationException.class);
    }


    @Nested
    class AppointmentQueries {

        @Test
        void getSingleAppointment() {
            testFixture.givenCommands("/appointment/register-appointment.json")
                    .whenQuery(new GetAppointment("myAppointment"))
                    .expectResult(AppointmentDetails.class);
        }

        @Test
        void findAppointments() {
            testFixture.givenCommands("/appointment/register-appointment.json")
                    .whenQuery(new FindAppointments("haircut"))
                    .expectResult(r -> r.size() == 1);
        }
    }



}
