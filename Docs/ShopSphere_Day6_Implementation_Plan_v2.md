# ShopSphere Day 6 – Phase-wise Implementation Plan

## Objective
Implement the Day 6 Microservices Lab by building three independent Spring Boot applications while satisfying every requirement defined in the lab.

---

# Phase 0 – Workspace & Project Setup

## Goal
Create the microservices workspace.

### Tasks
- Create `shopsphere-microservices`
- Create `order-service`
- Create `inventory-service`
- Create `notification-service`
- Create `docs`
- Create `postman`

### Deliverables
- Three independent Spring Boot projects
- Common workspace structure

### Validation
- All services start independently.

---

# Phase 1 – Order Service Foundation

## Goal
Build the Order Service.

### Tasks
- Spring Boot setup
- PostgreSQL configuration
- Order Entity
- Repository
- Service
- Controller
- DTOs
- Validation
- Global Exception Handler
- Logging

### Deliverables
- Order CRUD APIs

### Validation
- CRUD operations working
- Database connected

---

# Phase 2 – Inventory Service

## Goal
Build Inventory Service.

### Tasks
- Product Entity
- Inventory Entity
- Repository
- Service
- Controller
- Stock validation endpoint

### Deliverables
- Product & Inventory APIs

### Validation
- Product CRUD
- Stock validation endpoint

---

# Phase 3 – Notification Service

## Goal
Build Notification Service.

### Tasks
- Notification Entity
- Repository
- Service
- REST API (History)
- RabbitMQ Consumer placeholder

### Deliverables
- Notification History module

### Validation
- Notification records persisted

---

# Phase 4 – Database per Service

## Goal
Implement independent databases.

### Tasks
- orders_db
- inventory_db
- notification_db

### Validation
- Each service uses only its own database
- No cross-database access

---

# Phase 5 – WebClient Integration

## Goal
Implement synchronous communication.

### Tasks
- Configure WebClient
- Call Inventory Service
- Validate Product
- Validate Stock
- Handle timeout
- Handle HTTP 4xx
- Handle HTTP 5xx

### Validation
- Successful stock validation
- Order rejected when stock unavailable
- Failure scenarios verified

---

# Phase 6 – RabbitMQ Integration

## Goal
Implement asynchronous communication.

### Tasks
- Exchange
- Queue
- Binding
- Producer
- Consumer
- Publish:
  - Order Created
  - Order Updated
  - Order Cancelled
- Save Notification History
- Simulate Email/SMS

### Validation
- Events published
- Events consumed
- Notification history stored

---

# Phase 7 – Logging & Error Handling

## Goal
Implement enterprise logging.

### Tasks
- Request logging
- Response logging
- WebClient logging
- RabbitMQ logging
- Business logging
- Exception logging
- Communication failure logging

### Error Scenarios
- Inventory unavailable
- Timeout
- Invalid response
- RabbitMQ unavailable
- Consumer failure

### Validation
- Logs generated correctly
- Sensitive data not logged

---

# Phase 8 – End-to-End Integration

## Demo Flow

1. Start RabbitMQ
2. Start Inventory Service
3. Start Notification Service
4. Start Order Service
5. Create Product
6. Add Inventory
7. Create Order
8. Validate Inventory
9. Save Order
10. Publish RabbitMQ Event
11. Consume Event
12. Save Notification History
13. Simulate Email/SMS

### Validation
- Complete business flow successful

---

# Phase 9 – Final Evaluation Checklist

## Architecture
- [ ] Three independent Spring Boot applications
- [ ] Layered Architecture
- [ ] Database per Service

## Communication
- [ ] WebClient
- [ ] RabbitMQ

## Order Service
- [ ] CRUD
- [ ] Validation
- [ ] Logging

## Inventory Service
- [ ] Product Management
- [ ] Stock Validation

## Notification Service
- [ ] Event Consumer
- [ ] Notification History

## Error Handling
- [ ] HTTP 4xx
- [ ] HTTP 5xx
- [ ] Timeout
- [ ] Inventory Down
- [ ] RabbitMQ Failure

## Logging
- [ ] Request/Response
- [ ] Business Events
- [ ] Exceptions
- [ ] Inter-service Communication

## Final Deliverables
- Order Service
- Inventory Service
- Notification Service
- Three PostgreSQL Databases
- WebClient Integration
- RabbitMQ Integration
- Postman Collection
- PRD
- Implementation Plan
