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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * Base test class for API tests.
 * Configures REST Assured with base URL, content type, and logging.
 */
@Listeners({TestListener.class})
public class BaseApiTest {

    protected RequestSpecification requestSpec;

    @Step("Configure REST Assured")
    @BeforeClass(alwaysRun = true)
    public void setupClass() {
        RestAssured.baseURI = ApiConfig.getBaseUrl();

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured());

        if (ApiConfig.isRequestLoggingEnabled()) {
            builder.log(LogDetail.ALL);
        }

        requestSpec = builder.build();
    }
}
