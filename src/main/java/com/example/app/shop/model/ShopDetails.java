package com.example.app.shop.model;

import java.time.LocalTime;

public record ShopDetails(
        String shopId,
        String name,
        LocalTime openingTime,
        LocalTime closingTime
) {
}
