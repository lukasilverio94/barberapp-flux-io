package com.example.demo.user;

import com.example.demo.authentication.RequiresRole;
import com.example.demo.authentication.Role;
import io.fluxcapacitor.javaclient.modeling.AssertLegal;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import io.fluxcapacitor.javaclient.tracking.handling.IllegalCommandException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@RequiresRole(Role.admin)
public class CreateUser implements UserCommand {
    @NotNull UserId userId;
    @NotNull @Valid UserDetails details;
    Role role;

    @AssertLegal
    void assertNewUser(UserProfile profile) {
        if (profile != null) {
            throw new IllegalCommandException("User already exists");
        }
    }

    @Apply
    UserProfile apply() {
        return UserProfile.builder().userId(userId).details(details).role(role).build();
    }
}
