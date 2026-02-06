package com.dummyjson.models;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Product request model for /products endpoints.
 * Used for creating and updating products.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    private String title;
    private Integer price;

    public Product() {}

    public Product(String title, Integer price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("Product{title='%s', price=%d}", title, price);
    }
}
