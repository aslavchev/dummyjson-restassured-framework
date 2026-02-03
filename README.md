# DummyJSON REST Assured Framework

![CI](https://github.com/aslavchev/dummyjson-restassured-framework/actions/workflows/ci.yml/badge.svg)

API test automation framework for [DummyJSON](https://dummyjson.com/) using REST Assured and TestNG.

## Quick Start

```bash
# Clone the repository
git clone https://github.com/aslavchev/dummyjson-restassured-framework.git
cd dummyjson-restassured-framework

# Run tests
mvn clean test
```

## Tech Stack

- **Java 21**
- **REST Assured** - API testing
- **TestNG** - Test framework
- **Allure** - Reporting
- **Maven** - Build tool

## Project Structure

```
src/
├── main/java/com/dummyjson/
│   └── config/          # API configuration
└── test/java/com/dummyjson/
    ├── tests/           # Test classes
    ├── listeners/       # TestNG listeners
    └── testdata/        # Test data
```
