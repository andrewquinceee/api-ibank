package ru.netology.api;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(JSON)
            .setContentType(JSON)
            .build();

    public static User generateRandomActiveUser() {
        return new User("user" + faker.number().digits(5), faker.internet().password(), "active");
    }

    public static User generateRandomBlockedUser() {
        return new User("user" + faker.number().digits(5), faker.internet().password(), "blocked");
    }

    public static User generateUserWithInvalidLogin() {
        return new User("", faker.internet().password(), "active"); // Пустой логин
    }

    public static User generateUserWithInvalidPassword() {
        return new User("validuser" + faker.number().digits(5), "", "active"); // Пустой пароль
    }

    public static void register(User user) {
        given()
                .spec(requestSpec)
                .body(user)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
    }
    
    // Метод для тестов, где мы ожидаем ошибку (например, 400 Bad Request)
    public static void registerAndExpectError(User user, int expectedStatusCode) {
        given()
                .spec(requestSpec)
                .body(user)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(expectedStatusCode);
    }
}
