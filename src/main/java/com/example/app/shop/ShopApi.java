package com.example.app.shop;

import com.example.app.appointment.*;
import com.example.app.shop.command.*;
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
    ShopDetails registerShop(ShopDetails details) {
        ShopId id = FluxCapacitor.generateId(ShopId.class);
        Shop createdShop = FluxCapacitor.sendCommandAndWait(new CreateShop(id, details));
        return new ShopDetails(
                createdShop.shopId().toString(),
                createdShop.details().name(),
                createdShop.details().openingTime(),
                createdShop.details().closingTime()
        );
    }

    @HandleGet
    List<Shop> listShops(@QueryParam String term) {
        return FluxCapacitor.queryAndWait(new ListShops());
    }

    @HandleGet("/{shopId}")
    Shop getShop(@PathParam ShopId shopId) {
        return FluxCapacitor.queryAndWait(new GetShop(shopId));
    }

    @HandlePost("/{shopId}")
    ShopDetails updateShop(@PathParam ShopId shopId, ShopDetails details) {
        Shop updatedShop = FluxCapacitor.sendCommandAndWait(new UpdateShop(shopId, details));
        return new ShopDetails(
                updatedShop.shopId().toString(),
                updatedShop.details().name(),
                updatedShop.details().openingTime(),
                updatedShop.details().closingTime()
        );
    }

    @HandlePost("/{shopId}/appointments")
    Appointment createShopAppointment(@PathParam ShopId shopId, AppointmentDetailsRequest details) {
        AppointmentId appointmentId = FluxCapacitor.generateId(AppointmentId.class);
        Shop createdShop = FluxCapacitor.sendCommandAndWait(new CreateAppointment(shopId, appointmentId, details));
        var createdAppointment = createdShop.appointments().stream().filter(res -> res.appointmentId().equals(appointmentId))
                .findFirst();

        return createdAppointment.orElseThrow(() -> AppointmentErrors.genericError);
    }

//    @HandlePost("/{shopId}/appointments")
//    AppointmentId createShopAppointment(@PathParam ShopId shopId, AppointmentDetailsRequest details) {
//        AppointmentId appointmentId = FluxCapacitor.generateId(AppointmentId.class);
//        FluxCapacitor.sendCommandAndWait(new CreateAppointment(shopId, appointmentId, details));
//      return appointmentId;
//    }


    @HandleGet("/{shopId}/appointments")
    List<Appointment> getShopAppointments(@PathParam ShopId shopId) {
        return FluxCapacitor.queryAndWait(new ListShopAppointments(shopId));
    }

    @HandleDelete("/{shopId}")
    void deleteShop(@PathParam ShopId shopId) {
        FluxCapacitor.sendCommandAndWait(new DeleteShop(shopId));
    }


    // Dynamic -> pass on last parameter (accepted, requested or cancelled)
    @HandlePatch("/{shopId}/appointments/{appointmentId}/{appointmentStatus}")
    Shop changeAppointmentStatus(
            @PathParam ShopId shopId,
            @PathParam AppointmentId appointmentId,
            @PathParam AppointmentStatus appointmentStatus) {
        return FluxCapacitor.sendCommandAndWait(new UpdatedShopAppointmentStatus(shopId, appointmentId, appointmentStatus));
    }

}
