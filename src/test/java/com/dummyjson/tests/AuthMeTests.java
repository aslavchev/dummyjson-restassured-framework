package com.dummyjson.tests;

import com.dummyjson.helpers.AuthHelper;
import com.dummyjson.testdata.TestCredentials;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for token validation and authenticated session endpoints.
 */
public class AuthMeTests extends BaseApiTest {

    private static final String ME_ENDPOINT = "/auth/me";

    @Test(groups = {"smoke"})
    @Description("Verify valid token grants access to current user endpoint")
    public void testValidTokenReturnsUser() {
        // Arrange
        String accessToken = AuthHelper.getAccessToken(
                TestCredentials.VALID_USER.username(),
                TestCredentials.VALID_USER.password()
        );

        // Act & Assert
        given()
                .spec(requestSpec)
                .header("Authorization", AuthHelper.BEARER_PREFIX + accessToken)
        .when()
                .get(ME_ENDPOINT)
        .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("username", equalTo(TestCredentials.VALID_USER.username()));
    }

    @Test(groups = {"smoke"})
    @Description("Verify missing token returns 401 Unauthorized")
    public void testMissingTokenReturns401() {
        // Act & Assert - no token
        given()
                .spec(requestSpec)
        .when()
                .get(ME_ENDPOINT)
        .then()
                .statusCode(401)
                .body("message", notNullValue());
    }
}
