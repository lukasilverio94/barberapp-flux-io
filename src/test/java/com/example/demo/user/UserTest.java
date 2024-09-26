package com.example.demo.user;

import io.fluxcapacitor.javaclient.test.TestFixture;
import io.fluxcapacitor.javaclient.tracking.handling.authentication.UnauthorizedException;
import org.junit.jupiter.api.Test;

class UserTest {

    final TestFixture testFixture = TestFixture.create();

    @Test
    void createViewer() {
        testFixture.whenCommand("/user/create-viewer.json")
                .expectEvents("/user/create-viewer.json");
    }


    @Test
    void createUserAllowedAsEditor() {
        testFixture
                .givenCommands("/user/create-editor.json")
                .whenCommandByUser("editor", "/user/create-viewer.json")
                .expectEvents("/user/create-viewer.json");
    }

    @Test
    void createUserNotAllowedAsViewer() {
        testFixture.whenCommandByUser("viewer", "/user/create-editor.json")
                .expectExceptionalResult(UnauthorizedException.class);
    }

    @Test
    void findUser() {
        testFixture.givenCommands("/user/create-viewer.json")
                .whenQuery(new FindUsers("view"))
                .expectResult(r -> r.size() == 1);
    }
}