package com.dummyjson.tests;

import com.dummyjson.config.ApiConfig;
import com.dummyjson.listeners.TestListener;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

/**
 * Base test class for API tests.
 * Configures REST Assured with base URL, content type, and logging.
 */
@Listeners({TestListener.class})
public class BaseApiTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected RequestSpecification requestSpec;

    @Step("Initialize API configuration")
    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        ApiConfig.logConfiguration();
    }

    @Step("Configure REST Assured")
    @BeforeClass(alwaysRun = true)
    public void setupClass() {
        logger.info("Setting up REST Assured...");

        RestAssured.baseURI = ApiConfig.getBaseUrl();

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured());

        if (ApiConfig.isRequestLoggingEnabled()) {
            builder.log(LogDetail.ALL);
        }

        requestSpec = builder.build();

        logger.info("Base URL: {}", ApiConfig.getBaseUrl());
    }
}
