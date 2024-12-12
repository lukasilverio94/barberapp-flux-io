package com.example.demo.user;

import io.fluxcapacitor.javaclient.modeling.Id;

public class UserId extends Id<UserProfile> {
    public UserId(String id) {
        super(id);
    }
}
