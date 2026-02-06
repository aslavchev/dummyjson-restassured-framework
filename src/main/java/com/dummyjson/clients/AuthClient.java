package com.dummyjson.clients;

import com.dummyjson.config.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * API client for authentication endpoints.
 * Encapsulates HTTP calls to /auth/login and /auth/me.
 */
public class AuthClient {

    public static final String BEARER_PREFIX = "Bearer ";

    private final RequestSpecification requestSpec;

    /**
     * @param requestSpec shared REST Assured request specification
     */
    public AuthClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    /**
     * @param username DummyJSON username
     * @param password DummyJSON password
     * @return Response from POST /auth/login
     */
    @Step("Login with user: {username}")
    public Response login(String username, String password) {
        return given()
                .spec(requestSpec)
                .body(buildLoginBody(username, password))
            .when()
                .post(Endpoints.LOGIN);
    }

    /**
     * @param body raw JSON request body
     * @return Response from POST /auth/login
     */
    @Step("Login with request body")
    public Response loginWithBody(String body) {
        return given()
                .spec(requestSpec)
                .body(body)
            .when()
                .post(Endpoints.LOGIN);
    }

    /**
     * @param accessToken valid JWT access token
     * @return Response from GET /auth/me
     */
    @Step("Get current user with access token")
    public Response getAuthMe(String accessToken) {
        return given()
                .spec(requestSpec)
                .header("Authorization", BEARER_PREFIX + accessToken)
            .when()
                .get(Endpoints.AUTH_ME);
    }

    /**
     * @return Response from GET /auth/me without authorization header
     */
    @Step("Get current user without token")
    public Response getAuthMeWithoutToken() {
        return given()
                .spec(requestSpec)
            .when()
                .get(Endpoints.AUTH_ME);
    }

    /**
     * @param username DummyJSON username
     * @param password DummyJSON password
     * @return extracted accessToken string
     */
    @Step("Login and extract access token for user: {username}")
    public String getAccessToken(String username, String password) {
        return login(username, password)
            .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    private String buildLoginBody(String username, String password) {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
    }
}
