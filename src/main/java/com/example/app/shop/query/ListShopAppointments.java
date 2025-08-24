package com.example.app.shop.query;

import com.example.app.appointment.Appointment;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.handling.HandleQuery;
import io.fluxcapacitor.javaclient.tracking.handling.Request;

import java.util.List;

public record ListShopAppointments(ShopId shopId) implements Request<List<Appointment>> {

    @HandleQuery
    List<Appointment> handleQuery() {
        return FluxCapacitor.search(Appointment.class)
                .match(shopId, "shopId")
                .fetch(100);
    }
}
