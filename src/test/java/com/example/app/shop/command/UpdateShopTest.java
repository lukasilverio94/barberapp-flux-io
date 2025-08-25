package com.example.app.shop.command;

import com.example.app.shop.ShopErrors;
import com.example.app.util.BaseTest;
import org.junit.jupiter.api.Test;

public class UpdateShopTest extends BaseTest {

    @Test
    void renameShopSuccessfully() {
        fixture.givenCommands("/shop/create-shop.json")
                .whenCommand("/shop/update-shop.json")
                .expectEvents("/shop/update-shop.json");
    }

    @Test
    void renameNonExistingShop() {
        fixture.whenCommand("/shop/update-shop.json")
                .expectExceptionalResult(ShopErrors.notFound);
    }

    @Test
    void renamingToSameNameDoesNothing() {
        fixture.givenCommands("/shop/create-shop.json",
                        "/shop/update-shop.json")
                .whenCommand("/shop/update-shop.json")
                .expectNoEvents()
                .expectNoErrors();
    }
}
