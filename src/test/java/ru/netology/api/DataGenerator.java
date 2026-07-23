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

    public static String generateRandomLogin() {
        return "user" + faker.number().digits(5);
    }

    public static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static User generateUser(String status) {
        return new User(generateRandomLogin(), generateRandomPassword(), status);
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
}
