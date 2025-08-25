package com.example.app.shop.query;

import com.example.app.util.BaseTest;
import org.junit.jupiter.api.Test;

public class ListShopsTest extends BaseTest {

    @Test
    void listShopsReturnsAllRegisteredShops() {
        fixture.givenCommands("/shop/create-shop.json")
                .whenQuery(new ListShops())
                .expectResult(result -> !result.isEmpty());
    }
}
