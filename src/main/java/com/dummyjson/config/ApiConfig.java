package com.dummyjson.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized API configuration management.
 * Priority: System Environment Variables > .env file
 * Fails fast if required configuration is missing.
 *
 * Setup:
 * 1. Copy .env.example to .env
 * 2. Set API_USERNAME and API_PASSWORD
 */
public final class ApiConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApiConfig.class);
    private static final Dotenv dotenv;

    static {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        logger.info("Configuration loaded");
    }

    private ApiConfig() {
    }

    // ==================== Credentials ====================

    public static String getUsername() {
        return getRequiredEnv("API_USERNAME");
    }

    public static String getPassword() {
        return getRequiredEnv("API_PASSWORD");
    }

    // ==================== URLs ====================

    public static String getBaseUrl() {
        return getOptionalEnv("API_BASE_URL", "https://dummyjson.com");
    }

    // ==================== Timeouts ====================

    public static int getConnectionTimeout() {
        return Integer.parseInt(getOptionalEnv("API_CONNECTION_TIMEOUT", "10000"));
    }

    public static int getReadTimeout() {
        return Integer.parseInt(getOptionalEnv("API_READ_TIMEOUT", "10000"));
    }

    // ==================== Logging ====================

    public static boolean isRequestLoggingEnabled() {
        return Boolean.parseBoolean(getOptionalEnv("API_LOG_REQUESTS", "true"));
    }

    // ==================== Helper Methods ====================

    private static String getRequiredEnv(String key) {
        String value = getEnv(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException(
                    key + " not configured. Set in .env file or system environment."
            );
        }
        return value;
    }

    private static String getOptionalEnv(String key, String defaultValue) {
        String value = getEnv(key);
        if (value == null || value.isEmpty()) {
            logger.debug("Using default for {}: {}", key, defaultValue);
            return defaultValue;
        }
        return value;
    }

    private static String getEnv(String key) {
        String systemValue = System.getenv(key);
        if (systemValue != null && !systemValue.isEmpty()) {
            return systemValue;
        }
        String dotenvValue = dotenv.get(key);
        if (dotenvValue != null && !dotenvValue.isEmpty()) {
            return dotenvValue;
        }
        return null;
    }

    public static void logConfiguration() {
        logger.info("=== API Configuration ===");
        logger.info("Base URL: {}", getBaseUrl());
        logger.info("Username: {}", getUsername());
        logger.info("Password: ****");
        logger.info("Connection Timeout: {}ms", getConnectionTimeout());
        logger.info("Read Timeout: {}ms", getReadTimeout());
        logger.info("Request Logging: {}", isRequestLoggingEnabled());
        logger.info("=========================");
    }
}
