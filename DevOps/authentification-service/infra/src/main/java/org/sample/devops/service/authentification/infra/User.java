package org.sample.devops.service.authentification.infra;

public class User {

    private final String mail;
    private final String firstName;
    private final String lastName;
    private final String password;

    public User(String mail, String firstName, String lastName, String password) {
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}
