package com.example.demo.authentication;

import com.example.demo.user.UserId;
import com.example.demo.user.UserProfile;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.common.HasMessage;
import io.fluxcapacitor.javaclient.tracking.handling.authentication.AbstractUserProvider;
import io.fluxcapacitor.javaclient.tracking.handling.authentication.User;

public class SenderProvider extends AbstractUserProvider {

    public static final Sender system = Sender.builder()
            .userId(new UserId("system")).userRole(Role.admin).build();

    public SenderProvider() {
        super(Sender.class);
    }

    @Override
    public User fromMessage(HasMessage message) {
//        if (message instanceof DeserializingMessage dm && dm.getMessageType() == MessageType.WEBREQUEST) {
//            return AuthenticationUtils.getSender(dm.getMetadata());
//        }
        return super.fromMessage(message);
    }

    @Override
    public User getUserById(Object userId) {
        UserProfile userProfile = FluxCapacitor.loadAggregate(userId, UserProfile.class).get();
        return userProfile == null ? null : Sender.builder().userId(userProfile.getUserId()).build();
    }

    @Override
    public User getSystemUser() {
        return system;
    }
}
