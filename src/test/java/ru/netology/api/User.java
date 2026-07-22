package ru.netology.api;

import lombok.Value;

@Value
public class User {
    String login;
    String password;
    String status;
}
