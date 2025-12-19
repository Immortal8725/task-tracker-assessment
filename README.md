## Running with Docker

This project provides Docker and Docker Compose configurations for easy setup and deployment.

### Requirements
- Docker
- Docker Compose

### Services
- **java-tasktracker** (Spring Boot, Java 17)
  - Exposes port: `8080`
- **postgres-db** (PostgreSQL)
  - Exposes port: `5432`

### Environment Variables
- `POSTGRES_DB`: `tasktracker`
- `POSTGRES_USER`: `tasktracker`
- `POSTGRES_PASSWORD`: `tasktracker`

These are set by default in the `docker-compose.yml` for the database service.

### Build and Run
1. From the project root, run:
   ```sh
   docker compose up --build
   ```
   This will build the Java application and start both the app and PostgreSQL database.

2. Access the application at [http://localhost:8080](http://localhost:8080).

### Notes
- The application uses Java 17 (Eclipse Temurin images).
- The database is not persisted by default. To persist data, uncomment the `volumes` section for `postgres-db` in `docker-compose.yml`.
- If you need to customize environment variables, edit the `docker-compose.yml` or provide a `.env` file.

---
