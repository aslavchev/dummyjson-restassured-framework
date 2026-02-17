# DummyJSON REST Assured Framework

[![CI](https://github.com/aslavchev/dummyjson-restassured-framework/actions/workflows/ci.yml/badge.svg)](https://github.com/aslavchev/dummyjson-restassured-framework/actions/workflows/ci.yml)
[![Allure Report](https://img.shields.io/badge/Allure%20Report-Live-brightgreen)](https://aslavchev.github.io/dummyjson-restassured-framework/)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/)
[![REST Assured](https://img.shields.io/badge/REST%20Assured-5.5.7-green)](https://rest-assured.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.12-blue)](https://testng.org/)

API test automation framework for [DummyJSON](https://dummyjson.com/) using REST Assured and TestNG.

## Quick Start

```bash
# Clone repository
git clone https://github.com/aslavchev/dummyjson-restassured-framework.git
cd dummyjson-restassured-framework

# Configure credentials
cp .env.example .env

# Run tests
mvn clean test

# Run smoke tests only
mvn test -Dgroups=smoke

# Generate Allure report
mvn allure:serve
```

## Test Coverage

| Class | Tests | Endpoint |
|-------|-------|----------|
| LoginTests | 5 | POST /auth/login |
| AuthMeTests | 2 | GET /auth/me |
| RefreshTokenTests | 3 | POST /auth/refresh |
| ProductTests | 7 | GET/POST/PUT/DELETE /products |
| **Total** | **17** | |

## Project Structure

```
src/
├── main/java/com/dummyjson/
│   ├── config/
│   │   ├── ApiConfig.java          # Environment configuration
│   │   └── Endpoints.java          # API endpoint constants
│   ├── clients/
│   │   ├── AuthClient.java         # Auth API client (/auth/*)
│   │   └── ProductClient.java      # Products API client (/products/*)
│   └── models/
│       └── Product.java            # Product request POJO
│
└── test/java/com/dummyjson/
    ├── tests/
    │   ├── BaseApiTest.java        # REST Assured setup
    │   ├── LoginTests.java         # Login endpoint tests
    │   ├── AuthMeTests.java        # Token validation tests
    │   ├── RefreshTokenTests.java  # Refresh token lifecycle tests
    │   └── ProductTests.java       # Products CRUD tests
    ├── listeners/
    │   └── TestListener.java       # Test lifecycle logging
    └── testdata/
        └── TestCredentials.java    # Test user credentials
```

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Language |
| REST Assured 5.5.7 | API testing |
| TestNG 7.12.0 | Test framework |
| Allure 2.32.0 | Test reporting |
| Maven | Build tool |
| GitHub Actions | CI/CD |

## Configuration

Create a `.env` file from the example:

```bash
cp .env.example .env
```

Required variables:

```env
API_USERNAME=emilys
API_PASSWORD=emilyspass
```

Optional overrides:

```env
API_BASE_URL=https://dummyjson.com
API_CONNECTION_TIMEOUT=10000
API_READ_TIMEOUT=10000
API_LOG_REQUESTS=true
```

## Test Users

| User | Purpose |
|------|---------|
| `emilys` | Primary test user (from config) |
| `michaelw` | Secondary user for multi-user tests |
| `invalid_user` | Negative testing |

## Documentation

- [Architecture Decision Records](docs/adr/) — why REST Assured, why DummyJSON, client layer pattern, token management
- [Test Strategy](docs/TEST-STRATEGY.md) — coverage decisions, what's tested, what's not

## CI/CD

Tests run automatically on:
- Push to `main`
- Pull requests to `main`

Artifacts: Allure results

---

**Author:** Alex Slavchev
**Website:** [aslavchev.com](https://aslavchev.com)
