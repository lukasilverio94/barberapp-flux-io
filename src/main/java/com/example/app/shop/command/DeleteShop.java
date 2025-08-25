package com.example.app.shop.command;

import com.example.app.shop.ShopErrors;
import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import jakarta.annotation.Nullable;

public record DeleteShop(
        ShopId shopId
) implements ShopCommand {

    @AssertLegal
    void assertExists(@Nullable Shop shop) {
        if (shop == null) {
            throw ShopErrors.notFound;
        }
    }

    @Apply
    Shop apply() {
        return null;
    }
}
