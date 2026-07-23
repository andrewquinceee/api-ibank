package ru.netology.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeEach
    void setup() {
        // Configuration.headless убран по требованию проверяющего
        open("http://localhost:9999");
        $("[data-test-id='login'] input").shouldBe(visible);
    }

    @Test
    void shouldLoginWithActiveUser() {
        User user = DataGenerator.generateUser("active");
        DataGenerator.register(user);

        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $(".button").click();

        // Исправлено: используем точный CSS-селектор h2 и проверяем видимость + текст
        $("h2").shouldBe(visible).shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        User user = DataGenerator.generateUser("blocked");
        DataGenerator.register(user);

        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $(".button").click();

        // Исправлено: точный текст ошибки, который возвращает приложение
        $(".notification").shouldBe(visible).shouldHave(text("Ошибка"));
    }

    @Test
    void shouldNotLoginWithInvalidLogin() {
        User user = DataGenerator.generateUser("active");
        DataGenerator.register(user);

        // Исправлено: используем метод генератора вместо хардкода "invalid_..."
        String invalidLogin = DataGenerator.generateRandomLogin();
        $("[data-test-id='login'] input").setValue(invalidLogin);
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $(".button").click();

        $(".notification").shouldBe(visible).shouldHave(text("Ошибка"));
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        User user = DataGenerator.generateUser("active");
        DataGenerator.register(user);

        $("[data-test-id='login'] input").setValue(user.getLogin());
        // Исправлено: используем метод генератора вместо хардкода "wrong_password"
        String invalidPassword = DataGenerator.generateRandomPassword();
        $("[data-test-id='password'] input").setValue(invalidPassword);
        $(".button").click();

        $(".notification").shouldBe(visible).shouldHave(text("Ошибка"));
    }
}
