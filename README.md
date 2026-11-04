# Restaurant Platform

A small, API-first restaurant and menu platform rebuilt from the original Java exercise. It keeps the original restaurant hours and menu-pricing domain, adds validated JSON endpoints, database migrations, a sample restaurant, health checks, and an optional PostgreSQL connection. No paid service is required for local development.

## Run locally

Install Java 21. The Maven Wrapper downloads the pinned Maven build tool the first time you use it.

Windows:

```powershell
./mvnw.cmd spring-boot:run
```

macOS/Linux:

```sh
./mvnw spring-boot:run
```

The default profile uses an in-memory H2 database and seeds Amelie’s Kitchen. The API is available at `http://localhost:8080/api/v1/restaurants`; health is at `/actuator/health`.

## API

- `GET /api/v1/restaurants` — list restaurants and menus
- `GET /api/v1/restaurants/{id}` — restaurant details
- `POST /api/v1/restaurants` — create a restaurant (JSON with `name`, `location`, `openingTime`, `closingTime`, `menu[]`)
- `POST /api/v1/restaurants/{id}/quote` — calculate a validated order quote from menu item names and quantities

Example quote:

```json
{"items":[{"name":"Sweet corn soup","quantity":2},{"name":"Vegetable lasagne","quantity":1}]}
```

## PostgreSQL

Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` before running. Flyway owns schema changes under `src/main/resources/db/migration`; Hibernate validates the schema. Keep credentials in the hosting platform’s secret settings, never in source control. `Dockerfile` packages the executable jar on a Java 21 runtime image.

## Notes

This is a portfolio-grade reference service with input validation, conflict/not-found responses, a quote-only order flow, migrations, and readiness health checks. It intentionally does not accept payment or store customer data. Before public production use, add authenticated restaurant-owner operations, rate limits, observability export, and deployment-specific secret management.
