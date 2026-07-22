package ru.netology.api;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginTest {

    @BeforeEach
    void setup() {
        Configuration.baseUrl = "http://localhost:9999";
        open("/login");
    }

    @Test
    void shouldLoginWithActiveUser() {
        User activeUser = DataGenerator.generateUser("active");
        DataGenerator.register(activeUser);
        
        $("[data-test-id='login']").setValue(activeUser.getLogin());
        $("[data-test-id='password']").setValue(activeUser.getPassword());
        $("[data-test-id='login-button']").click();
        
        $("[data-test-id='dashboard']").shouldBe(visible);
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        User blockedUser = DataGenerator.generateUser("blocked");
        DataGenerator.register(blockedUser);
        
        $("[data-test-id='login']").setValue(blockedUser.getLogin());
        $("[data-test-id='password']").setValue(blockedUser.getPassword());
        $("[data-test-id='login-button']").click();
        
        $("[data-test-id='error-message']").shouldHave(visible);
    }

    @Test
    void shouldNotLoginWithInvalidCredentials() {
        User validUser = DataGenerator.generateUser("active");
        DataGenerator.register(validUser);
        
        // Неправильный логин
        $("[data-test-id='login']").setValue("invalid_" + validUser.getLogin());
        $("[data-test-id='password']").setValue(validUser.getPassword());
        $("[data-test-id='login-button']").click();
        
        $("[data-test-id='error-message']").shouldHave(visible);
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        User validUser = DataGenerator.generateUser("active");
        DataGenerator.register(validUser);
        
        $("[data-test-id='login']").setValue(validUser.getLogin());
        $("[data-test-id='password']").setValue("wrong_password");
        $("[data-test-id='login-button']").click();
        
        $("[data-test-id='error-message']").shouldHave(visible);
    }
}
