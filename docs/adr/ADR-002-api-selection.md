# ADR-002: DummyJSON API Selection

## Status

Accepted

## Context

Need a public API to build the test automation framework against. Requirements: authentication support, CRUD endpoints, reliable uptime, and a domain that complements the Selenium project (SauceDemo e-commerce).

## Decision

Use DummyJSON (dummyjson.com) as the target API.

## Why DummyJSON

- **JWT authentication** — real token-based auth flow (login, get token, use token)
- **E-commerce domain** — products, carts, users. Matches SauceDemo from the Selenium project, creating a consistent portfolio story
- **Reliable uptime** — hosted independently, not on Heroku free tier
- **Rich endpoints** — supports GET, POST, PUT, DELETE, search, pagination. Enough variety for meaningful test coverage

## Other APIs Considered

- **ReqRes** and **Swagger Petstore** were evaluated but DummyJSON had the best fit: JWT auth for realistic testing and an e-commerce domain matching the Selenium project
- **Restful-Booker** was rejected — hosted on Heroku (intermittent downtime, cold starts) and data resets every 10 minutes, making tests unreliable

## Consequences

- CRUD operations are simulated (POST returns a response but doesn't persist) — tests validate response structure, not database state
- Portfolio tells a consistent e-commerce story across both Selenium and API projects
