package ru.netology.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ApiTest {

    @Test
    @DisplayName("Успешная регистрация активного пользователя")
    void shouldRegisterActiveUserSuccessfully() {
        User user = DataGenerator.generateRandomActiveUser();
        DataGenerator.register(user);
    }

    @Test
    @DisplayName("Успешная регистрация заблокированного пользователя")
    void shouldRegisterBlockedUserSuccessfully() {
        User user = DataGenerator.generateRandomBlockedUser();
        DataGenerator.register(user);
    }

    @Test
    @DisplayName("Перезапись данных существующего пользователя (возвращает 200)")
    void shouldOverwriteExistingUserSuccessfully() {
        User user = DataGenerator.generateRandomActiveUser();
        DataGenerator.register(user); // Первая регистрация
        
        // Вторая регистрация с тем же логином, но новым паролем и статусом
        User updatedUser = new User(user.getLogin(), "newPassword123", "blocked");
        DataGenerator.register(updatedUser); // Должно вернуть 200 (перезапись)
    }

    @Test
    @DisplayName("Реакция на невалидный логин (пустая строка): приложение возвращает 200")
    void shouldReturn200ForEmptyLogin() {
        User user = DataGenerator.generateUserWithInvalidLogin();
        // Мы изучили реакцию: приложение принимает пустой логин и возвращает 200
        DataGenerator.registerAndExpectError(user, 200); 
    }

    @Test
    @DisplayName("Реакция на невалидный пароль (пустая строка): приложение возвращает 200")
    void shouldReturn200ForEmptyPassword() {
        User user = DataGenerator.generateUserWithInvalidPassword();
        // Мы изучили реакцию: приложение принимает пустой пароль и возвращает 200
        DataGenerator.registerAndExpectError(user, 200); 
    }
}
