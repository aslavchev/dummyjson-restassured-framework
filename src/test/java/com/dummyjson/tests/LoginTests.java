package com.dummyjson.tests;

import com.dummyjson.clients.AuthClient;
import com.dummyjson.testdata.TestCredentials;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
    @Description("Verify empty request body returns 400")
    public void testEmptyBodyReturns400() {
        // Act & Assert
        authClient.loginWithBody("{}")
        .then()
                .statusCode(400)
                .body("message", equalTo("Username and password required"));
    }

    @Test(dataProvider = "invalidLogins", groups = {"smoke"})
    @Description("Verify invalid login scenarios return appropriate errors")
    public void testInvalidLoginReturnError(String username, String password, int expectedStatusCode, String expectedErrorMessage) {
        // Act & Assert
        authClient.login(username, password)
        .then()
                .statusCode(expectedStatusCode)
                .body("message", equalTo(expectedErrorMessage));
    }

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLoginData() {
        return new Object[][] {
                {"invalid_user", "wrongpassword", 400, "Invalid credentials"},
                {"", "emilyspass", 400, "Username and password required"},
                {"emilys", "", 400, "Username and password required"}
        };
    }
}
