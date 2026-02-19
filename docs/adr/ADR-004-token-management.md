# ADR-004: Per-Test Token Management

## Status

Accepted

## Context

DummyJSON requires JWT (JSON Web Token) Bearer tokens for authenticated endpoints (auth/me, product CRUD). Need a strategy for obtaining and using tokens across tests.

## Decision

Each test obtains its own fresh token by calling `authClient.getAccessToken()`. Tokens are stored as local variables within the test method, not shared across tests.

## Why Per-Test Tokens

- **Test independence** — no test depends on another test's token. Tests can run in any order
- **Simple and explicit** — the Arrange step of each test shows exactly how it gets its token. No hidden setup
- **Matches the Selenium pattern** — each Selenium test gets a fresh browser session. Each API test gets a fresh token. Same principle: start clean
- **21 tests total** — the performance cost of an extra login call per test is negligible at this scale

## Consequences

- Every authenticated test has a clear Arrange, Act, Assert structure
- `@BeforeClass` sets up the client objects (the tools), but tokens are never cached or shared — each test gets its own
- Slightly more API calls than caching would require — acceptable trade-off for test isolation
- If the test suite grows significantly, could revisit with per-class token caching
