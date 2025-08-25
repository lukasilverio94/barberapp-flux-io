package com.example.app.shop.command;

import com.example.app.shop.ShopErrors;
import com.example.app.util.BaseTest;
import org.junit.jupiter.api.Test;

public class CreateShopTest extends BaseTest {

    @Test
    void successfullyCreateShop() {
        fixture.whenCommand("/shop/create-shop.json")
                .expectEvents("/shop/create-shop.json");
    }

    @Test
    void creatingDuplicateShopIsRejected() {
        fixture.givenCommands("/shop/create-shop.json")
                .whenCommand("/shop/create-shop.json")
                .expectExceptionalResult(ShopErrors.alreadyExists);
    }
}
