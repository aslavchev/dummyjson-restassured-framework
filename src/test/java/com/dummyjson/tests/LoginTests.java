package com.dummyjson.tests;

import com.dummyjson.clients.AuthClient;
import com.dummyjson.testdata.TestCredentials;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for authentication login functionality.
 */
public class LoginTests extends BaseApiTest {

    private AuthClient authClient;

    @BeforeClass(alwaysRun = true)
    public void setupClients() {
        authClient = new AuthClient(requestSpec);
    }

    @Test(groups = {"smoke"})
    @Description("Verify valid login returns 200 with tokens")
    public void testValidCredentialsReturnTokens() {
        // Act & Assert
        authClient.login(
                TestCredentials.VALID_USER.username(),
                TestCredentials.VALID_USER.password()
        )
        .then()
                .statusCode(200)
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("username", equalTo(TestCredentials.VALID_USER.username()));
    }

    @Test(groups = {"smoke"})
    @Description("Verify invalid credentials return 400 with error message")
    public void testInvalidCredentialsReturn400() {
        // Act & Assert
        authClient.login(
                TestCredentials.INVALID_USER.username(),
                TestCredentials.INVALID_USER.password()
        )
        .then()
                .statusCode(400)
                .body("message", equalTo("Invalid credentials"));
    }

    @Test(groups = {"smoke"})
    @Description("Verify empty request body returns 400")
    public void testEmptyBodyReturns400() {
        // Act & Assert
        authClient.loginWithBody("{}")
        .then()
                .statusCode(400)
                .body("message", equalTo("Username and password required"));
    }
}
