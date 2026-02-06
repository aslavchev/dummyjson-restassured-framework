package com.dummyjson.tests;

import com.dummyjson.clients.ProductClient;
import com.dummyjson.models.Product;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * Tests for products endpoint CRUD operations.
 */
public class ProductTests extends BaseApiTest {

    private static final int INVALID_PRODUCT_ID = 99999;
    private ProductClient productClient;

    @BeforeClass(alwaysRun = true)
    public void setupClients() {
        productClient = new ProductClient(requestSpec);
    }

    // ============ READ OPERATIONS ============

    @Test(groups = {"smoke"})
    @Description("Verify get all products returns 200")
    public void testGetAllProductsReturns200() {
        // Act & Assert
        productClient.getAllProducts()
        .then()
                .statusCode(200)
                .body("products", notNullValue())
                .body("total", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify get product by ID returns 200")
    public void testGetProductByIdReturns200() {
        // Act & Assert
        productClient.getProductById(1)
        .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify get product by invalid ID returns 404")
    public void testGetProductByInvalidIdReturns404() {
        // Act & Assert
        productClient.getProductById(INVALID_PRODUCT_ID)
        .then()
                .statusCode(404)
                .body("message", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify search products returns results")
    public void testSearchProductsReturnsResults() {
        // Act & Assert
        productClient.searchProducts("laptop")
        .then()
                .statusCode(200)
                .body("products", notNullValue());
    }

    // ============ WRITE OPERATIONS ============

    @Test(groups = {"smoke"})
    @Description("Verify add product returns 201")
    public void testAddProductReturns201() {
        // Arrange
        Product product = new Product("Test Product", 99);

        // Act & Assert
        productClient.addProduct(product)
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Test Product"));
    }

    @Test(groups = {"smoke"})
    @Description("Verify update product returns 200")
    public void testUpdateProductReturns200() {
        // Arrange
        Product product = new Product("Updated Title", null);

        // Act & Assert
        productClient.updateProduct(1, product)
        .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"));
    }

    @Test(groups = {"smoke"})
    @Description("Verify delete product returns 200")
    public void testDeleteProductReturns200() {
        // Act & Assert
        productClient.deleteProduct(1)
        .then()
                .statusCode(200)
                .body("isDeleted", equalTo(true));
    }
}
