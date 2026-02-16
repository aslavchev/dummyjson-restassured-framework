package com.dummyjson.tests;

import com.dummyjson.clients.AuthClient;
import com.dummyjson.testdata.TestCredentials;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * Tests for refresh token functionality.
 */
public class RefreshTokenTests extends BaseApiTest {

    private AuthClient authClient;

    @BeforeClass(alwaysRun = true)
    public void setupClients() {
        authClient = new AuthClient(requestSpec);
    }

    @Test(groups = {"smoke"})
    @Description("Verify refresh token returns new access and refresh tokens")
    public void testValidRefreshTokenReturnsNewTokens() {
        // Arrange
        String refreshToken = authClient.login(
                TestCredentials.VALID_USER.username(),
                TestCredentials.VALID_USER.password()
        ).then().extract().path("refreshToken");

        // Act & Assert
        authClient.refreshSession(refreshToken, 30)
        .then()
                .statusCode(200)
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify invalid refresh token returns error")
    public void testInvalidRefreshTokenReturnsError() {
        // Act & Assert
        authClient.refreshSession("invalid-garbage-token", 30)
        .then()
                .statusCode(403)
                .body("message", equalTo("Invalid refresh token"));
    }

    @Test(groups = {"smoke"})
    @Description("Verify refreshed access token is usable for authenticated requests")
    public void testRefreshedTokenIsUsable() {
        // Arrange
        String refreshToken = authClient.login(
                TestCredentials.VALID_USER.username(),
                TestCredentials.VALID_USER.password()
        ).then().extract().path("refreshToken");

        String newAccessToken = authClient.refreshSession(refreshToken, 30)
        .then()
                .statusCode(200)
                .extract().path("accessToken");

        // Act & Assert
        authClient.getAuthMe(newAccessToken)
        .then()
                .statusCode(200)
                .body("username", equalTo(TestCredentials.VALID_USER.username()));
    }
}
