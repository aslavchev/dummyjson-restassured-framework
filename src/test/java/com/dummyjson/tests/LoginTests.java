package com.dummyjson.tests;

import com.dummyjson.helpers.AuthHelper;
import com.dummyjson.testdata.TestCredentials;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends BaseApiTest {

    private static final String LOGIN_ENDPOINT = "/auth/login";

    @Test(groups = {"smoke"})
    @Description("Verify valid login returns 200 with tokens")
    public void testValidCredentialsReturnTokens() {
        // Arrange
        String requestBody = AuthHelper.buildLoginBody(
                TestCredentials.VALID_USER.username(),
                TestCredentials.VALID_USER.password()
        );

        // Act & Assert
        given()
                .spec(requestSpec)
                .body(requestBody)
        .when()
                .post(LOGIN_ENDPOINT)
        .then()
                .statusCode(200)
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("username", equalTo(TestCredentials.VALID_USER.username()));
    }

    @Test(groups = {"smoke"})
    @Description("Verify invalid credentials return 400 with error message")
    public void testInvalidCredentialsReturn400() {
        // Arrange
        String requestBody = AuthHelper.buildLoginBody(
                TestCredentials.INVALID_USER.username(),
                TestCredentials.INVALID_USER.password()
        );

        // Act & Assert
        given()
                .spec(requestSpec)
                .body(requestBody)
        .when()
                .post(LOGIN_ENDPOINT)
        .then()
                .statusCode(400)
                .body("message", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify empty request body returns 400")
    public void testEmptyBodyReturns400() {
        // Arrange
        String requestBody = "{}";

        // Act & Assert
        given()
                .spec(requestSpec)
                .body(requestBody)
        .when()
                .post(LOGIN_ENDPOINT)
        .then()
                .statusCode(400)
                .body("message", notNullValue());
    }
}
