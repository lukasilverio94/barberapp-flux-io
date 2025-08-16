package com.example.app.user;

import jakarta.validation.constraints.NotBlank;

public record UserDetails(
        @NotBlank String name,
        @NotBlank String phone,
        @NotBlank String email,
        @NotBlank String password
        ) {
}
