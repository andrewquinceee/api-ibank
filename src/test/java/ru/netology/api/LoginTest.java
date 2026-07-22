package ru.netology.api;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeEach
    void setup() {
        Configuration.baseUrl = "http://localhost:9999";
        open("/");
        // Ждем появления формы, чтобы убедиться, что страница загрузилась
        $("[data-test-id='login'] input").shouldBe(visible);
    }

    @Test
    void shouldLoginWithActiveUser() {
        User user = DataGenerator.generateUser("active");
        DataGenerator.register(user);

        $("[data-test-id='login'] input").shouldBe(visible).setValue(user.getLogin());
        $("[data-test-id='password'] input").shouldBe(visible).setValue(user.getPassword());
        $("[data-test-id='action']").shouldBe(visible).click();

        $(".dashboard").shouldBe(visible);
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        User user = DataGenerator.generateUser("blocked");
        DataGenerator.register(user);

        $("[data-test-id='login'] input").shouldBe(visible).setValue(user.getLogin());
        $("[data-test-id='password'] input").shouldBe(visible).setValue(user.getPassword());
        $("[data-test-id='action']").shouldBe(visible).click();

        $(".notification").shouldBe(visible).shouldHave(text("Ошибка"));
    }

    @Test
    void shouldNotLoginWithInvalidLogin() {
        User user = DataGenerator.generateUser("active");
        DataGenerator.register(user);

        $("[data-test-id='login'] input").shouldBe(visible).setValue("invalid_" + user.getLogin());
        $("[data-test-id='password'] input").shouldBe(visible).setValue(user.getPassword());
        $("[data-test-id='action']").shouldBe(visible).click();

        $(".notification").shouldBe(visible).shouldHave(text("Ошибка"));
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        User user = DataGenerator.generateUser("active");
        DataGenerator.register(user);

        $("[data-test-id='login'] input").shouldBe(visible).setValue(user.getLogin());
        $("[data-test-id='password'] input").shouldBe(visible).setValue("wrong_password");
        $("[data-test-id='action']").shouldBe(visible).click();

        $(".notification").shouldBe(visible).shouldHave(text("Ошибка"));
    }
}
