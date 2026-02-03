package com.dummyjson.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener for test lifecycle events.
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("========================================");
        logger.info("Starting: {}", context.getName());
        logger.info("========================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("========================================");
        logger.info("Finished: {}", context.getName());
        logger.info("Passed: {} | Failed: {} | Skipped: {}",
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
        logger.info("========================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info(">>> {}", getTestName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("<<< PASSED: {} ({}ms)", getTestName(result), getDuration(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("<<< FAILED: {}", getTestName(result));
        logger.error("Reason: {}", result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("<<< SKIPPED: {}", getTestName(result));
    }

    private String getTestName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName()
                + "." + result.getMethod().getMethodName();
    }

    private long getDuration(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }
}
