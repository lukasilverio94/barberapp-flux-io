package com.example.app.shop;

import com.example.app.shop.command.CreateShop;
import com.example.app.shop.model.Shop;
import com.example.app.shop.model.ShopDetails;
import com.example.app.util.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShopApiTest extends BaseTest {

    @BeforeEach
    void setup() {
        fixture.registerHandlers(new ShopApi());
    }

    @Test
    void createShopSuccessfully() {
        fixture.whenPost("/shops", "/shop/create-shop-request.json")
                .expectResult(ShopDetails.class)
                .<ShopDetails>expectResult(response ->
                    response.name().equals("test")
                            && response.openingTime().equals(LocalTime.of(8, 0, 0))
                            && response.closingTime().equals(LocalTime.of(20, 30, 0))
                )
                .expectEvents(CreateShop.class);
    }

    @Test
    void listShopsSuccessfully() {
        fixture.givenPost("/shops", "/shop/create-shop-request.json")
                .whenGet("/shops")
                .<List<ShopDetails>>expectResult(res ->
                        !res.isEmpty());
    }

//    @Test
//    void getShopByIdSuccesfully() {
//        fixture.givenPost("/shops", "/shop/create-shop-request.json")
//                .whenGet("/shops/{shopId}");
//    }
    //todo: how do I get the result from the givenPost?
}
