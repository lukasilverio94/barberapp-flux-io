package com.example.app.shop.command;

import com.example.app.shop.ShopErrors;
import com.example.app.util.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteShopTest extends BaseTest {

    @Test
    void shouldDeleteShopSuccessfullyReturnsNull() {
        fixture.givenCommands("/shop/create-shop.json")
                .whenCommand("/shop/delete-shop.json")
                .expectResult();
    }

    @Test
    @DisplayName("When not existing shop is passed - should return Shop not found")
    void deleteNonExistingShopThrowsException() {
        fixture.whenCommand("/shop/delete-shop.json")
                .expectExceptionalResult(ShopErrors.notFound);
    }
}
