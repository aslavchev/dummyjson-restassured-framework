package com.dummyjson.tests;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

/**
 * Smoke test to verify CI pipeline and base configuration.
 */
public class SmokeTest {

    @Test(groups = {"smoke"})
    public void testCiPipelineWorks() {
        // Arrange
        boolean ciIsConfigured = true;

        // Act & Assert
        assertTrue(ciIsConfigured, "CI pipeline should be configured and working");
    }
}
