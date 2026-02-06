package com.dummyjson.clients;

import com.dummyjson.config.Endpoints;
import com.dummyjson.models.Product;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * API client for products endpoints.
 * Encapsulates HTTP calls to /products/*.
 */
public class ProductClient {

    private final RequestSpecification requestSpec;

    /**
     * @param requestSpec shared REST Assured request specification
     */
    public ProductClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    /**
     * @return Response from GET /products
     */
    @Step("Get all products")
    public Response getAllProducts() {
        return given()
                .spec(requestSpec)
            .when()
                .get(Endpoints.PRODUCTS);
    }

    /**
     * @param id product ID
     * @return Response from GET /products/{id}
     */
    @Step("Get product by ID: {id}")
    public Response getProductById(int id) {
        return given()
                .spec(requestSpec)
            .when()
                .get(Endpoints.PRODUCTS + "/" + id);
    }

    /**
     * @param query search keyword
     * @return Response from GET /products/search?q={query}
     */
    @Step("Search products with query: {query}")
    public Response searchProducts(String query) {
        return given()
                .spec(requestSpec)
            .when()
                .get(Endpoints.PRODUCTS + "/search?q=" + query);
    }

    /**
     * @param product request payload
     * @return Response from POST /products/add
     */
    @Step("Add new product")
    public Response addProduct(Product product) {
        return given()
                .spec(requestSpec)
                .body(product)
            .when()
                .post(Endpoints.PRODUCTS + "/add");
    }

    /**
     * @param id product ID to update
     * @param product request payload with updated fields
     * @return Response from PUT /products/{id}
     */
    @Step("Update product ID: {id}")
    public Response updateProduct(int id, Product product) {
        return given()
                .spec(requestSpec)
                .body(product)
            .when()
                .put(Endpoints.PRODUCTS + "/" + id);
    }

    /**
     * @param id product ID to delete
     * @return Response from DELETE /products/{id}
     */
    @Step("Delete product ID: {id}")
    public Response deleteProduct(int id) {
        return given()
                .spec(requestSpec)
            .when()
                .delete(Endpoints.PRODUCTS + "/" + id);
    }
}
