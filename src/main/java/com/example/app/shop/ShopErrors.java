package com.example.app.shop;

import io.fluxcapacitor.javaclient.tracking.handling.IllegalCommandException;


public interface ShopErrors {

    IllegalCommandException alreadyExists = illegalCommandException("Shop already exists"),
            notFound = illegalCommandException("Shop not found"),
            outOfService = illegalCommandException("Barbershop is closed at this time. Check the availability");

    static IllegalCommandException illegalCommandException(String message) {
        return new IllegalCommandException(message);
    }
}
