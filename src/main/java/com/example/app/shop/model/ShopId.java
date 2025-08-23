package com.example.app.shop.model;

import io.fluxcapacitor.javaclient.modeling.Id;

public class ShopId extends Id<Shop> {
    public ShopId(String id){
        super(id);
    }
}
