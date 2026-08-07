# ShopSphere Day 8 – Product Requirements Document (Updated)

## Overview
Enhance the existing Day 7 platform (reuse project) with Docker, Docker Compose, Logging and Spring Boot Actuator.

## Reuse
- Config Server
- Eureka
- API Gateway
- Common Security Module
- Order, Inventory, Notification Services
- PostgreSQL
- RabbitMQ

## Mandatory Day 7 Improvements
- Replace hardcoded URLs with Eureka service discovery.
- Gateway validates JWT.
- Shared security module reused.
- Implement RBAC and Authority-based permissions:
  - ORDER_READ/CREATE/UPDATE/DELETE
  - INVENTORY_READ/UPDATE
  - NOTIFICATION_READ/SEND
- Map authorities to roles.

## Day 8 Scope
- Dockerfiles for every service
- Docker images (local build using Podman)
- Docker Compose (local deployment for application services only)
- Connect to existing PostgreSQL and RabbitMQ containers
- SLF4J logging (development environment, INFO level)
- Spring Boot Actuator (public endpoints for monitoring)
- Deployment validation

## Validation
- No localhost service calls
- Eureka discovery
- Docker Compose startup
- Gateway routing
- Logging
- Actuator endpoints
