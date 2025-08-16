package com.example.app.user;

import com.example.app.authentication.Role;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.web.HandleGet;
import io.fluxcapacitor.javaclient.web.HandlePost;
import io.fluxcapacitor.javaclient.web.PathParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersEndpoint {
    @HandlePost("/users/{userType}")
    UserId createBarber(UserDetails details, @PathParam UserType userType) {
        var userId = new UserId();
        FluxCapacitor.sendCommandAndWait(new CreateUser(userId, details, userType.getRole()));
        return userId;
    }

    @HandleGet("/users")
    List<UserProfile> getUsers() {
        return FluxCapacitor.queryAndWait(new GetUsers());
    }

}
