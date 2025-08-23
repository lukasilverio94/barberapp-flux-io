package com.example.app.shop.command;

import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopDetails;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record CreateShop(ShopId shopId,
                         @Valid @NotNull ShopDetails details) implements ShopCommand {

    @Apply
    Shop apply() {
        log.info("===========================Entrou no método CreateShop.apply()===========================");
        return Shop.builder()
                .details(details)
                .build();
    }
}
