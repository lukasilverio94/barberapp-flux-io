package com.example.app.shop.command;

import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopDetails;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import io.fluxcapacitor.javaclient.tracking.handling.IllegalCommandException;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateShop(
        ShopId shopId,
        @Valid @NotNull ShopDetails details
        )  implements ShopCommand{

    @AssertLegal
    void assertShopExists(@Nullable Shop shop) {
        if (shop == null) {
            throw new IllegalCommandException("This shop doesn't exist");
        }
    }

    @Apply
    Shop apply(Shop shop) {
        return shop.toBuilder()
                .details(details)
                .build();
    }
}
