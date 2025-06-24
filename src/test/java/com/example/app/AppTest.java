package com.example.app;

import com.example.app.user.GetUsers;
import io.fluxcapacitor.javaclient.test.TestFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class AppTest {

	@Autowired TestFixture testFixture;

	@Test
	void createUser() {
		testFixture.whenCommand("/user/create-viewer.json")
				.expectEvents("/user/create-viewer.json");
	}

	@Test
	void getUsers() {
		testFixture.givenCommands("/user/create-viewer.json")
				.whenQuery(new GetUsers())
				.expectResult(r -> r.size() == 1);
	}
}
