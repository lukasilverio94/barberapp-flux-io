package com.example.demo.user;

import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.tracking.handling.HandleQuery;
import io.fluxcapacitor.javaclient.tracking.handling.Request;
import lombok.Value;

import java.util.List;

@Value
public class FindUsers implements Request<List<UserProfile>> {
    String term;

    @HandleQuery
    List<UserProfile> handle() {
        return FluxCapacitor.search(UserProfile.class).lookAhead(term).fetchAll();
    }

}
