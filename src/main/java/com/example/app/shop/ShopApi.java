package com.example.app.shop;

import com.example.app.appointment.api.common.Appointment;
import com.example.app.appointment.api.common.AppointmentDetails;
import com.example.app.appointment.api.common.AppointmentDetailsRequest;
import com.example.app.appointment.api.common.AppointmentId;
import com.example.app.shop.command.CreateShop;
import com.example.app.shop.command.CreateShopAppointment;
import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopDetails;
import com.example.app.shop.model.ShopId;
import com.example.app.shop.query.GetShop;
import com.example.app.shop.query.ListShopAppointments;
import com.example.app.shop.query.ListShops;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.web.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Path("/shops")
public class ShopApi {

    @HandlePost
    ShopId registerShop(ShopDetails details) {
        ShopId id = FluxCapacitor.generateId(ShopId.class);
        FluxCapacitor.sendCommandAndWait(new CreateShop(id, details));
        return id;
    }

    @HandleGet
    List<Shop> listShops(@QueryParam String term) {
        return FluxCapacitor.queryAndWait(new ListShops());
    }

    @HandleGet("/{shopId}")
    Shop getShop(@PathParam ShopId shopId) {
        return FluxCapacitor.queryAndWait(new GetShop(shopId));
    }

    @HandlePost("/{shopId}/appointments")
    ShopId createShopAppointment(@PathParam ShopId shopId, AppointmentDetailsRequest details) {
        AppointmentId appointmentId = FluxCapacitor.generateId(AppointmentId.class);
        return FluxCapacitor.sendCommandAndWait(new CreateShopAppointment(shopId, appointmentId, details));
    }

    @HandleGet("/{shopId}/appointments")
    List<Appointment> getShopAppointments(@PathParam ShopId shopId) {
        return FluxCapacitor.queryAndWait(new ListShopAppointments(shopId));
    }
//
//    @HandleGet("/{shopId}")
//    Shop getAppointment(@PathParam ShopId shopId) {
//        return FluxCapacitor.queryAndWait();
//    }
//
//    @HandlePost("/{shopId}")
//    CompletableFuture<ShopId> updateShop(@PathParam ShopId shopId, Shop requestDetails) {
//        return FluxCapacitor.sendCommand();
//    }
}
