package com.example.app.shop.query;

import com.example.app.shop.model.Shop;
import com.example.app.util.BaseTest;
import org.junit.jupiter.api.Test;

public class GetShopTest extends BaseTest {

    @Test
    void getShopReturnsDocument() {
        fixture.givenCommands("/shop/create-shop.json")
                .whenQuery("/shop/get-shop.json")
                .expectResult(Shop.class);
    }
}
