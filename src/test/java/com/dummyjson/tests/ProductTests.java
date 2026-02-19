package com.dummyjson.tests;

import com.dummyjson.clients.ProductClient;
import com.dummyjson.models.Product;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * Tests for products endpoint CRUD operations.
 */
public class ProductTests extends BaseApiTest {

    private static final int VALID_PRODUCT_ID = 18;
    private static final int MUTABLE_PRODUCT_ID = 1;
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
                .body("products.size()", greaterThan(0))
                .body("total", greaterThan(0));
    }

    @Test(groups = {"smoke"})
    @Description("Verify get product by ID returns 200")
    public void testGetProductByIdReturns200() {
        // Act & Assert
        productClient.getProductById(VALID_PRODUCT_ID)
        .then()
                .statusCode(200)
                .body("id", equalTo(VALID_PRODUCT_ID))
                .body("title", notNullValue());
    }

    @Test(groups = {"smoke"})
    @Description("Verify get product by invalid ID returns 404")
    public void testGetProductByInvalidIdReturns404() {
        // Act & Assert
        productClient.getProductById(INVALID_PRODUCT_ID)
        .then()
                .statusCode(404)
                .body("message", equalTo("Product with id '" + INVALID_PRODUCT_ID + "' not found"));
    }

    @Test(groups = {"smoke"})
    @Description("Verify search products returns results")
    public void testSearchProductsReturnsResults() {
        // Act & Assert
        productClient.searchProducts("laptop")
        .then()
                .statusCode(200)
                .body("products.size()", greaterThan(0))
                .body("total", greaterThan(0))
                .body("products[0].category", equalTo("laptops"));
    }

    @Test(groups = {"regression"})
    @Description("Verify search with no matching products returns empty results")
    public void testSearchNonExistentProductReturnsEmpty() {
        // Act & Assert
        productClient.searchProducts("nonexistentproduct12345")
        .then()
                .statusCode(200)
                .body("products.size()", equalTo(0))
                .body("total", equalTo(0));
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
                .body("id", greaterThan(0))
                .body("title", equalTo("Test Product"));
    }

    @Test(groups = {"smoke"})
    @Description("Verify update product returns 200")
    public void testUpdateProductReturns200() {
        // Arrange
        Product product = new Product("Updated Title", null);

        // Act & Assert
        productClient.updateProduct(MUTABLE_PRODUCT_ID, product)
        .then()
                .statusCode(200)
                .body("id", equalTo(MUTABLE_PRODUCT_ID))
                .body("title", equalTo("Updated Title"));
    }

    @Test(groups = {"smoke"})
    @Description("Verify delete product returns 200")
    public void testDeleteProductReturns200() {
        // Act & Assert
        productClient.deleteProduct(MUTABLE_PRODUCT_ID)
        .then()
                .statusCode(200)
                .body("id", equalTo(MUTABLE_PRODUCT_ID))
                .body("isDeleted", equalTo(true));
    }
    /**
     * Test data for pagination scenarios: {limit, skip, expectedSize, expectedLimit}.
     */
    @DataProvider(name = "paginationData")
    public Object[][] paginationData() {
        return new Object[][] {
                {5, 0, 5, 5},      // first page
                {5, 10, 5, 5},     // middle page
                {5, 9999, 0, 0},   // beyond total
        };
    }

    /**
     * @param limit items per page
     * @param skip items to skip (offset)
     * @param expectedSize expected number of returned products
     * @param expectedLimit expected limit in response
     */
    @Test(dataProvider = "paginationData", groups = {"regression"})
    @Description("Verify pagination with limit and skip returns correct subset")
    public void testGetProductsPaginationReturnsCorrectSubset(int limit, int skip, int expectedSize, int expectedLimit) {
        // Act & Assert
        productClient.getProducts(limit, skip)
        .then()
                .statusCode(200)
                .body("limit", equalTo(expectedLimit))
                .body("skip", equalTo(skip))
                .body("products.size()", equalTo(expectedSize));
    }
}
