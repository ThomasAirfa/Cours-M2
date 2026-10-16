package org.sample.devops.service.authentification;

import org.junit.jupiter.api.Test;
import org.sample.devops.service.authentification.dto.AuthentificationRequest;
import org.sample.devops.service.authentification.dto.AuthentificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class HttpRequestUnitTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void authentificationShouldReturnNotFoundErrorWhenIncorrectPassword() throws Exception {
        var baseURL = "http://localhost:" + port + "/api/authenticate";
        var uri = new URI(baseURL);
        var request = new AuthentificationRequest("user", "password");
        var response = this.restTemplate.postForEntity(uri, request, AuthentificationResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void authentificationShouldReturnUserWhenCorrectMailAndPassword() throws Exception {
        var baseURL = "http://localhost:" + port + "/api/authenticate";
        var uri = new URI(baseURL);
        var request = new AuthentificationRequest("user1@mail.com", "password1");
        var response = this.restTemplate.postForEntity(uri, request, AuthentificationResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new AuthentificationResponse("user1@mail.com", "User1FirstName", "User1LastName"));
    }
}
