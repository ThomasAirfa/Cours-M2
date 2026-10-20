package org.sample.devops.service.authentification.exposition;

import org.sample.devops.service.authentification.domain.AuthentificationService;
import org.sample.devops.service.authentification.exposition.AuthentificationRequest;
import org.sample.devops.service.authentification.exposition.AuthentificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthentificationController {
    private final AuthentificationService authentificationService;

    public AuthentificationController(AuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthentificationResponse> authenticate(@RequestBody AuthentificationRequest authentificationRequest) {
        var userOptional = authentificationService.authenticate(authentificationRequest.mail(), authentificationRequest.password());
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var authentificationResponse = new AuthentificationResponse(user.getMail(), user.getFirstName(), user.getLastName());
            return ResponseEntity.ok(authentificationResponse);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
