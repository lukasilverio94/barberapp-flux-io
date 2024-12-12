package com.example.app.user;

import com.example.app.authentication.Role;
import io.fluxcapacitor.javaclient.modeling.Aggregate;
import lombok.Builder;
import lombok.Value;

@Aggregate(searchable = true)
@Value
@Builder(toBuilder = true)
public class UserProfile {
    UserId userId;
    UserDetails details;
    Role role;
}
