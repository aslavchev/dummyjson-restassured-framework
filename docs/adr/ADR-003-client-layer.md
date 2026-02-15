# ADR-003: API Client Layer Pattern

## Status

Accepted

## Context

Tests initially made direct REST Assured calls — building requests, setting headers, and calling endpoints inline. As the test suite grew across 3 endpoint groups (login, auth/me, products), the test code became repetitive. The same authorization header setup appeared in multiple tests.

The Selenium project already used Page Objects to wrap UI interactions. Needed the same separation for API calls.

## Decision

Introduce dedicated API client classes — AuthClient and ProductClient — that encapsulate all HTTP calls. Tests call client methods instead of building REST Assured requests directly.

## Why This Pattern

- **Same idea as Page Objects** — Page Objects wrap browser interactions, API clients wrap HTTP calls. Same architectural pattern across both projects
- **Tests stay clean** — a test reads as `authClient.getAccessToken()` instead of a 6-line REST Assured chain
- **DRY** — authorization headers, content types, and endpoint paths defined once in the client, not repeated in every test
- **Allure integration** — client methods use `@Step` annotations, so the Allure report shows meaningful steps like "Login and extract access token" instead of raw HTTP calls

## Consequences

- Tests are shorter and focused on assertions, not request construction
- Adding a new endpoint test means adding one method to the client, not duplicating boilerplate
- Clients live in `src/main`, tests in `src/test` — clean separation of infrastructure and test logic
- Slight indirection — to understand what HTTP call a test makes, you need to look at the client class
