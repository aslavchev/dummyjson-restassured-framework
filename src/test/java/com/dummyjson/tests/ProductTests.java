package com.dummyjson.tests;

import com.dummyjson.config.Endpoints;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for products endpoint CRUD operations.
 */
public class ProductTests extends BaseApiTest {

    @Test(groups = {"smoke"})
    @Description("Verify get all products returns 200")
    public void testGetAllProductsReturns200() {
        // Act & Assert
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.PRODUCTS)
        .then()
                .statusCode(200)
                .body("products", notNullValue())
                .body("total", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify get product by ID returns 200")
    public void testGetProductByIdReturns200() {
        // Act & Assert
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.PRODUCTS + "/1")
        .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify get product by invalid ID returns 404")
    public void testGetProductByInvalidIdReturns404() {
        // Act & Assert
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.PRODUCTS + "/99999")
        .then()
                .statusCode(404)
                .body("message", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify search products returns results")
    public void testSearchProductsReturnsResults() {
        // Act & Assert
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.PRODUCTS + "/search?q=laptop")
        .then()
                .statusCode(200)
                .body("products", notNullValue());
    }

    // ============ WRITE OPERATIONS ============

    @Test(groups = {"smoke"})
    @Description("Verify add product returns 201")
    public void testAddProductReturns201() {
        // Arrange
        String requestBody = "{\"title\":\"Test Product\",\"price\":99}";

        // Act & Assert
        given()
                .spec(requestSpec)
                .body(requestBody)
        .when()
                .post(Endpoints.PRODUCTS + "/add")
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Test Product"));

    }

    @Test(groups = {"smoke"})
    @Description("Verify update product returns 200")
    public void testUpdateProductReturns200() {
        // Arrange
        String requestBody = "{\"title\":\"Updated Title\"}";

        // Act & Assert
        given()
                .spec(requestSpec)
                .body(requestBody)
        .when()
                .put(Endpoints.PRODUCTS + "/1")
        .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"));
    }

    @Test(groups = {"smoke"})
    @Description("Verify delete product returns 200")
    public void testDeleteProductReturns200() {
        // Act & Assert
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.PRODUCTS + "/1")
        .then()
                .statusCode(200)
                .body("isDeleted", equalTo(true));
    }

}
