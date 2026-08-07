# ShopSphere Day 8 – Phase-wise Implementation Plan (Updated)

## Phase 1
Implement authority-based authorization and map authorities to roles.

## Phase 0
Review Day 7, replace localhost URLs with Eureka, verify shared security.

## Phase 2
Create Dockerfiles for Config, Eureka, Gateway, Order, Inventory and Notification services.
Build Docker images locally using Podman.

## Phase 3
Create docker-compose.yml for application services only (Config, Eureka, Gateway, Order, Inventory, Notification).
Configure external connections to existing PostgreSQL and RabbitMQ containers.
Use default network configuration.

## Phase 4
Configure SLF4J logging (startup, business, security, exceptions) at INFO level for development.

## Phase 5
Enable Global Exception logging at INFO level.

## Phase 6
Enable Spring Boot Actuator (health, info, metrics, env, beans, mappings) with public access.

## Phase 7
Validate:
- Eureka discovery
- No hardcoded URLs
- JWT via Gateway
- RBAC + Authorities
- Docker (Podman)
- Compose
- External PostgreSQL/RabbitMQ connectivity
- Logging
- Actuator
