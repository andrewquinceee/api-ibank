package ru.netology.api;

import lombok.Data;

@Data
public class User {
    private String login;
    private String password;
    private String status;

    public User(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }
}
