# ShopSphere Order Management Platform

# Day 6 – Product Requirements Document (PRD)

## 1. Project Information

| Item | Value |
|------|-------|
| Project Name | ShopSphere Order Management Platform |
| Lab | Day 6 – Microservices Architecture |
| Technology | Java 17, Spring Boot 3.x |
| Build Tool | Maven |
| Database | PostgreSQL |
| Messaging | RabbitMQ |
| Communication | REST + WebClient |
| Architecture | Microservices |

## 2. Project Objective

Transform the ShopSphere Order Management Platform into a Microservices-based architecture by decomposing the application into independent services.

Features:
- Microservices Architecture
- Database per Service
- WebClient
- RabbitMQ
- Logging
- Error Handling

## 3. Existing System

A new microservices workspace will be created. Previous labs serve as design references only; no source code will be copied directly.

## 4. Workspace Structure

```text
shopsphere-microservices/
├── order-service
├── inventory-service
├── notification-service
├── docs
├── postman
└── README.md
```

## 5. Microservices

### Order Service
- Create / Update / Cancel Orders
- Validate inventory
- Publish order events
- Database: orders_db

### Inventory Service
- Manage products
- Validate stock
- Update stock
- Database: inventory_db

### Notification Service
- Consume events
- Store notification history
- Simulate Email/SMS
- Database: notification_db

## 6. Communication

### Synchronous
- Spring WebClient
- Order Service → Inventory Service

### Asynchronous
- RabbitMQ
- Order Service → Notification Service

## 7. Logging

- Startup
- Request / Response
- WebClient
- RabbitMQ
- Business Events
- Exceptions

## 8. Error Handling

Handle:
- Inventory unavailable
- Timeouts
- HTTP 4xx / 5xx
- RabbitMQ failures
- Consumer failures

## 9. Non-Functional Requirements

- Independent deployment
- Layered Architecture
- Loose Coupling
- Scalability
- Maintainability

## 10. Out of Scope

- Eureka
- API Gateway
- Config Server
- Docker
- Kubernetes
- Circuit Breaker

## 11. Success Criteria

- Three independent Spring Boot services
- Three PostgreSQL databases
- WebClient integration
- RabbitMQ integration
- Logging
- Error handling
- End-to-end order processing

## 12. Requirement Coverage Matrix

| Requirement | Implementation |
|------------|----------------|
| Independent Services | 3 Spring Boot Projects |
| Database per Service | 3 PostgreSQL DBs |
| WebClient | Order → Inventory |
| RabbitMQ | Order → Notification |
| Logging | SLF4J |
| Error Handling | Global + Communication |

## 13. Deliverables

- Order Service
- Inventory Service
- Notification Service
- PostgreSQL Databases
- RabbitMQ
- WebClient
- Postman Collection
- Documentation
