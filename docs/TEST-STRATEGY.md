# Test Strategy

## Overview

This document outlines the test strategy for the DummyJSON REST Assured Framework, explaining the approach to API test selection and coverage decisions.

## API Under Test

**DummyJSON** (dummyjson.com) is a free API that simulates an e-commerce backend. It provides:
- JWT authentication with access and refresh tokens
- Product catalog with CRUD operations
- Search and pagination
- Predictable responses for testing (CRUD is simulated — responses return expected structures but don't persist to a database)

## Endpoint Coverage

### Authentication (10 tests)

| Endpoint | Method | Tests | What's Verified |
|----------|--------|-------|-----------------|
| `/auth/login` | POST | 5 | Valid login returns tokens; data-driven invalid scenarios (wrong credentials, empty username, empty password) |
| `/auth/me` | GET | 2 | Valid token returns user data, missing token returns 401 |
| `/auth/refresh` | POST | 3 | Valid refresh returns new tokens, invalid token returns 403, refreshed token is usable |

### Products (11 tests)

| Endpoint | Method | Tests | What's Verified |
|----------|--------|-------|-----------------|
| `/products` | GET | 1 | Returns product list with total count |
| `/products?limit&skip` | GET | 3 | Data-driven pagination via @DataProvider (first page, middle page, beyond total) |
| `/products/{id}` | GET | 2 | Valid ID returns product, invalid ID returns 404 |
| `/products/search` | GET | 2 | Search returns matching products; search with no results returns empty array |
| `/products/add` | POST | 1 | Create returns 201 with product data |
| `/products/{id}` | PUT | 1 | Update returns 200 with modified fields |
| `/products/{id}` | DELETE | 1 | Delete returns 200 with isDeleted flag |

**Total: 21 tests across 4 test classes**

## What's Tested

- **Happy path** — valid login, valid tokens, successful CRUD
- **Token lifecycle** — login, refresh, and proof that refreshed tokens work on protected endpoints
- **Data-driven testing** — login negative scenarios and pagination via @DataProvider
- **Error handling** — invalid credentials, missing tokens, invalid refresh tokens, non-existent resources
- **Response structure** — status codes, field presence, correct values
- **HTTP methods** — GET, POST, PUT, DELETE

## What's NOT Tested

| Gap | Reason |
|-----|--------|
| **Data persistence** | DummyJSON simulates CRUD — POST/PUT/DELETE return expected responses but don't modify the database. Tests validate response structure, not database state |
| **Rate limiting** | Not relevant for a demo API |
| **Pagination edge cases** | Basic pagination tested (first page, middle page, beyond total); sorting and field selection (`select` param) not tested |
| **Performance / load** | Requires separate tooling, different skill set |
| **Schema validation** | Considered but not implemented — would require maintaining JSON schema files |

## Test Data Management

- **Credentials:** Environment variables via `ApiConfig` (GitHub Secrets in CI)
- **Test users:** `TestCredentials` enum — `VALID_USER` (from config), `INVALID_USER` (hardcoded), `EMPTY_USER`
- **Product data:** Created inline using `Product` POJO — no external test data files

## CI/CD Integration

- **Trigger:** Every push to `main`, all PRs, manual dispatch
- **Runtime:** Java 21 (Temurin)
- **Build:** Maven with dependency caching
- **Artifacts:** Allure results + report (30-day retention)
- **Live report:** Deployed to GitHub Pages via separate workflow
- **Success criteria:** 100% pass rate

## Metrics

| Metric | Target | Measurement |
|--------|--------|-------------|
| Pass Rate | 100% | Allure report |
| Flakiness | 0% | CI history |
| Execution Time | < 1 min | CI logs |
| Coverage | All endpoints exercised | Manual review |
