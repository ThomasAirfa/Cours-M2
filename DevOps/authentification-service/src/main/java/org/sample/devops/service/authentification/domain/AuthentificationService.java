package org.sample.devops.service.authentification.domain;

import org.sample.devops.service.authentification.db.User;
import org.sample.devops.service.authentification.db.UserDB;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthentificationService {
    private final UserDB userDB;

    public AuthentificationService(UserDB userDB) {
        this.userDB = userDB;
    }

    public Optional<User> authenticate(String mail, String password) {
        var user = userDB.getUserByMail(mail);
        if (user.isPresent()) {
                if (user.get().getPassword().equals(password)) {
                    return user;
                }
        }
        return Optional.empty();
    }
}
