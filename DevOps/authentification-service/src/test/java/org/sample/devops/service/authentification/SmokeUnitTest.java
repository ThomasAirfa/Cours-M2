package org.sample.devops.service.authentification;

import org.junit.jupiter.api.Test;
import org.sample.devops.service.authentification.controller.AuthentificationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeUnitTest {

    @Autowired
    private AuthentificationController authentificationController;

    @Test
    void loadContext() throws Exception {
        assertThat(authentificationController).isNotNull();
    }
}
