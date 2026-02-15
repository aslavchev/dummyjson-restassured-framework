# ADR-001: REST Assured for API Testing

## Status

Accepted

## Context

The API framework targets DummyJSON endpoints covering auth, CRUD, and error handling. Need a Java library that supports JSON validation, chained assertions, and integrates with the existing TestNG + Allure stack from the Selenium project.

## Decision

Use REST Assured for all API test automation.

## Why REST Assured

- **Industry standard** for Java API testing — most SDET job postings list it
- **Matches existing stack** — already using Java 21, TestNG, and Allure in the Selenium project. REST Assured fits without introducing a new language or toolchain
- **Proven in training** — covered in Pragmatic LLC Web Services testing course, so the patterns are familiar
- **BDD-style syntax** — `given().auth().when().get().then().statusCode(200)` reads like documentation, making test intent clear in code reviews

## Consequences

- Tests read like specifications — easy to review, easy to onboard
- Shared TestNG + Allure infrastructure across Selenium and API projects
- JSON path assertions built in — no additional assertion libraries needed
- Can be verbose for simple GET requests, but readability is worth the trade-off
