package com.example.app.shop.query;

import com.example.app.shop.model.Shop;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.handling.HandleQuery;
import io.fluxcapacitor.javaclient.tracking.handling.Request;

import java.util.List;

public record ListShops() implements Request<List<Shop>> {

    @HandleQuery
    List<Shop> handleQuery() {
        return FluxCapacitor.search(Shop.class).fetch(100);
    }
}
