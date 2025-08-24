package com.example.app.shop.model;

import com.example.app.appointment.Appointment;
import io.fluxcapacitor.javaclient.modeling.Aggregate;
import io.fluxcapacitor.javaclient.modeling.EntityId;
import io.fluxcapacitor.javaclient.modeling.EventPublication;
import io.fluxcapacitor.javaclient.modeling.Member;
import lombok.Builder;
import lombok.With;

import java.util.List;

@Aggregate(searchable = true, eventPublication = EventPublication.IF_MODIFIED)
@Builder(toBuilder = true)
public record Shop(
        @EntityId ShopId shopId,
        ShopDetails details,
        @With @Member List<Appointment> appointments) {

}
