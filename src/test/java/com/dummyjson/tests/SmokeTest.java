package com.dummyjson.tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Smoke test to verify CI pipeline and base configuration.
 */
public class SmokeTest extends BaseApiTest {

    @Test(groups = {"smoke"})
    @Description("Verify CI pipeline executes tests successfully")
    public void testCiPipelineWorks() {
        // Arrange
        boolean ciIsConfigured = true;

        // Act & Assert
        assertTrue(ciIsConfigured, "CI pipeline should be configured and working");
    }
}
