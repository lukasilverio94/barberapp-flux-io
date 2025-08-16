package com.example.app.user;

import com.example.app.authentication.Role;

public enum UserType {
    barber(Role.barber),
    customer(Role.customer);

    private final Role role;

    UserType(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
