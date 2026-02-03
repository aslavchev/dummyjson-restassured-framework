package com.dummyjson.testdata;

import com.dummyjson.config.ApiConfig;

/**
 * Test credentials for DummyJSON API.
 * Uses real users from DummyJSON database.
 *
 * @see <a href="https://dummyjson.com/users">DummyJSON Users</a>
 */
public enum TestCredentials {

    /**
     * Primary test user from config.
     * Use for happy path testing.
     */
    VALID_USER {
        @Override public String username() { return ApiConfig.getUsername(); }
        @Override public String password() { return ApiConfig.getPassword(); }
    },

    /**
     * Secondary valid user with own credentials.
     * Use for multi-user scenarios.
     */
    VALID_USER_2("michaelw", "michaelwpass"),

    /**
     * Invalid credentials for negative testing.
     */
    INVALID("invalid_user", "wrong_password"),

    /**
     * Empty credentials for validation testing.
     */
    EMPTY("", "");

    private final String username;
    private final String password;

    /**
     * Constructor for config-based credentials (VALID_USER).
     */
    TestCredentials() {
        this.username = null;
        this.password = null;
    }

    /**
     * Constructor for custom credentials.
     */
    TestCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("TestCredentials[%s]", name());
    }
}
