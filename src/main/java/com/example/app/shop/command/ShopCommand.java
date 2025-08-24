package com.example.app.shop.command;

import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopId;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.Consumer;
import io.fluxcapacitor.javaclient.tracking.TrackSelf;
import io.fluxcapacitor.javaclient.tracking.handling.HandleCommand;
import jakarta.validation.constraints.NotNull;

@TrackSelf
@Consumer(name = "shop-commands")
public interface ShopCommand {

    @NotNull ShopId shopId();

    @HandleCommand
    default Shop handle() {
        return FluxCapacitor.loadAggregate(shopId()).assertAndApply(this).get();
    }
}
