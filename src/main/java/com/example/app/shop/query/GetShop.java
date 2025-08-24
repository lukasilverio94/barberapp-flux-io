package com.example.app.shop.query;

import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.handling.HandleQuery;
import io.fluxcapacitor.javaclient.tracking.handling.Request;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record GetShop(@NotNull ShopId shopId) implements Request<Shop> {

    @HandleQuery
    Shop handleQuery() {
        return FluxCapacitor.search(Shop.class)
                .query(shopId.toString(), "shopId")
                .fetchFirstOrNull();
    }
}
