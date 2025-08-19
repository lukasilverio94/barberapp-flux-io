package com.example.app.appointment;

import com.example.app.appointment.command.ApproveAppointment;
import com.example.app.appointment.command.RejectAppointment;
import com.example.app.appointment.query.FindAppointments;
import com.example.app.appointment.query.GetAppointment;
import com.example.app.appointment.command.RegisterAppointment;
import com.example.app.appointment.command.UpdateAppointment;
import com.example.app.appointment.api.common.AppointmentDetails;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentId;
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
    CompletableFuture<AppointmentId> registerAppointment(AppointmentDetailsRequest requestDetails) {
        log.info("Entrou no metodo apply no register controller");
        return FluxCapacitor.sendCommand(new RegisterAppointment(requestDetails));
    }

    @HandleGet("/{appointmentId}")
    CompletableFuture<AppointmentDetails> getAppointmentById(@PathParam AppointmentId appointmentId) {
        return FluxCapacitor.query(new GetAppointment(appointmentId));
    }

    @HandleGet
    CompletableFuture<List<AppointmentDetails>> getAppointments(@QueryParam String term) {
        return FluxCapacitor.query(new FindAppointments(term));
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
