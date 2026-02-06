package com.dummyjson.tests;

import com.dummyjson.clients.AuthClient;
import com.dummyjson.testdata.TestCredentials;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for token validation and authenticated session endpoints.
 */
public class AuthMeTests extends BaseApiTest {

    private AuthClient authClient;

    @BeforeClass(alwaysRun = true)
    public void setupClients() {
        authClient = new AuthClient(requestSpec);
    }

    @Test(groups = {"smoke"})
    @Description("Verify valid token grants access to current user endpoint")
    public void testValidTokenReturnsUser() {
        // Arrange
        String accessToken = authClient.getAccessToken(
                TestCredentials.VALID_USER.username(),
                TestCredentials.VALID_USER.password()
        );

        // Act & Assert
        authClient.getAuthMe(accessToken)
        .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("username", equalTo(TestCredentials.VALID_USER.username()));
    }

    @Test(groups = {"smoke"})
    @Description("Verify missing token returns 401 Unauthorized")
    public void testMissingTokenReturns401() {
        // Act & Assert
        authClient.getAuthMeWithoutToken()
        .then()
                .statusCode(401)
                .body("message", notNullValue());
    }
}
