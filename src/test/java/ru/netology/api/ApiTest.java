package ru.netology.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ApiTest {

    @Test
    @DisplayName("Успешная регистрация активного пользователя")
    void shouldRegisterActiveUserSuccessfully() {
        User user = DataGenerator.generateUser("active");
        DataGenerator.register(user);
    }

    @Test
    @DisplayName("Успешная регистрация заблокированного пользователя")
    void shouldRegisterBlockedUserSuccessfully() {
        User user = DataGenerator.generateUser("blocked");
        DataGenerator.register(user);
    }

    @Test
    @DisplayName("Перезапись данных существующего пользователя (возвращает 200)")
    void shouldOverwriteExistingUserSuccessfully() {
        User user = DataGenerator.generateUser("active");
        DataGenerator.register(user); 
        
        // Используем тот же логин, но новые данные (перезапись)
        User updatedUser = new User(user.getLogin(), "newPassword123", "blocked");
        DataGenerator.register(updatedUser); 
    }
}
