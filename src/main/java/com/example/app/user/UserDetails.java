package com.example.app.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UserDetails {
    @NotBlank String name;
}
