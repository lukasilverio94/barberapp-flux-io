package com.example.app.appointment.command;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.ShopAppointmentErrors;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.tracking.TrackSelf;
import io.fluxcapacitor.javaclient.tracking.handling.HandleCommand;
import jakarta.annotation.Nullable;

@TrackSelf
public interface AppointmentCreate extends AppointmentCommand {

    // When you call the implementation of this command, in this RegisterAppointment
    // (which implements this interface), the first method called(apparently) is this one (handle)
    // inside the method we can see that it calls loadAggregate(appointmentId()), which loads
    // the current appointment record by looking up the id from appointmentId().
    // After it finds that record, it starts applying the methods annotated with @AssertLegal,
    // both the ones implemented inside this interface and the ones implemented
    // inside the records that implements this interface (RegisterAppointment)
    @HandleCommand
    default Appointment handle() {
        System.out.println("Method called ---> AppointmentCreate.handle() ========================");
        var appointmentCreated = FluxCapacitor.loadAggregate(appointmentId()).assertAndApply(this);
        return appointmentCreated.get();
    }

    // This one only checks for existence because it's a create method
    @AssertLegal
    default void assertExistence(@Nullable Appointment current) {
        System.out.println("Method called ---> AppointmentCreate.assertExistence() ========================");
        if (current != null) {
            throw ShopAppointmentErrors.alreadyExists;
        }
    }


}
