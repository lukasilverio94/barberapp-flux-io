package com.example.demo.authentication;

import com.example.demo.user.UserId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.fluxcapacitor.javaclient.tracking.handling.authentication.User;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Sender implements User {

    public static Sender getCurrent() {
        return User.getCurrent();
    }

    @NonNull UserId userId;
    UserId impersonator;
    Role userRole;

    @Override
    @JsonIgnore
    public String getName() {
        return userId.getFunctionalId();
    }

    @Override
    public boolean hasRole(String role) {
        return Role.valueOf(role).matches(userRole);
    }

    public boolean isAdmin() {
        return Role.admin.matches(userRole);
    }
}
