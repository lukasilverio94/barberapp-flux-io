package com.example.app.appointment;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.command.*;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentId;
import com.example.app.appointment.query.GetAppointment;
import com.example.app.appointment.query.FindAppointments;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.web.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@Path("/appointments")
public class AppointmentApi {

    @HandlePost
    AppointmentId registerAppointment(AppointmentDetailsRequest details) {
        AppointmentId id = FluxCapacitor.generateId(AppointmentId.class);
        FluxCapacitor.sendCommandAndWait(new CreateAppointment(id, details));
        return id;
    }

    @HandleGet
    CompletableFuture<List<Appointment>> getAppointments(@QueryParam String term) {
        System.out.println("Entrou no metodo AppointmentApi.getAppointments");
        return FluxCapacitor.query(new FindAppointments(term));
    }

    @HandleGet("/{appointmentId}")
    Appointment getAppointment(@PathParam AppointmentId appointmentId) {
        System.out.println("Appointment ID = " + appointmentId);
        return FluxCapacitor.queryAndWait(new GetAppointment(appointmentId));
    }

    @HandlePost("/{appointmentId}")
    CompletableFuture<AppointmentId> updateAppointment(@PathParam AppointmentId appointmentId, AppointmentDetailsRequest requestDetails) {
        return FluxCapacitor.sendCommand(new UpdateAppointment(appointmentId, requestDetails));
    }

    @HandlePost("/{appointmentId}/approve")
    CompletableFuture<AppointmentId> approveAppointment(@PathParam AppointmentId appointmentId) {
        return FluxCapacitor.sendCommand(new ApproveAppointment(appointmentId));
    }

    @HandlePost("/{appointmentId}/reject")
    CompletableFuture<AppointmentId> rejectAppointment(@PathParam AppointmentId appointmentId) {
        return FluxCapacitor.sendCommand(new RejectAppointment(appointmentId));
    }


}
