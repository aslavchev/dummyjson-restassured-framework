package com.dummyjson.helpers;

import com.dummyjson.config.ApiConfig;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

/**
 * Reusable helpers for authentication operations.
 */
public class AuthHelper {

    public static final String LOGIN_ENDPOINT = "/auth/login";
    public static final String BEARER_PREFIX = "Bearer ";

    @Step("Build login request body for user: {username}")
    public static String buildLoginBody(String username, String password) {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
    }

    @Step("Login and get access token for user: {username}")
    public static String getAccessToken(String username, String password) {
        return given()
                .baseUri(ApiConfig.getBaseUrl())
                .contentType(ContentType.JSON)
                .body(buildLoginBody(username, password))
            .when()
                .post(LOGIN_ENDPOINT)
            .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }
}
