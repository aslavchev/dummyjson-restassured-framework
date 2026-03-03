package com.dummyjson.config;

/**
 * Centralized API configuration management.
 * Reads from environment variables with sensible defaults for local development.
 */
public final class ApiConfig {

    private ApiConfig() {
    }

    // ==================== Credentials ====================

    public static String getUsername() {
        return env("API_USERNAME", "emilys");
    }

    public static String getPassword() {
        return env("API_PASSWORD", "emilyspass");
    }

    // ==================== URLs ====================

    public static String getBaseUrl() {
        return env("API_BASE_URL", "https://dummyjson.com");
    }

    // ==================== Logging ====================

    public static boolean isRequestLoggingEnabled() {
        return Boolean.parseBoolean(env("API_LOG_REQUESTS", "true"));
    }

    // ==================== Helper Methods ====================

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
}
