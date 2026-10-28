package fr.uge.jee.anotations.messenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Messenger {
    private final String token;

    public Messenger(@Value("${MESSENGER_TOKEN}") String token) {
        this.token = Objects.requireNonNull(token);
    }

    public void send(String message){
        System.out.println("Using the super secret token "+token+" to send the message : "+message);
    }
}