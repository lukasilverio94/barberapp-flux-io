package com.example.app.shop.command;

import com.example.app.shop.ShopErrors;
import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopDetails;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record CreateShop(ShopId shopId,
                         @Valid @NotNull ShopDetails details) implements ShopCommand {

    @AssertLegal
    void assertIsNotDuplicated(@Nullable Shop shop) {
        if (shop != null) {
            throw ShopErrors.alreadyExists;
        }
    }

    @Apply
    Shop apply() {
        return Shop.builder()
                .shopId(shopId)
                .details(details)
                .build();
    }
}
