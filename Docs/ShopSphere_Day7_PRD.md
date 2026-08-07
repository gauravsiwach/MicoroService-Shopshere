# ShopSphere Day 7 – Product Requirements Document (PRD)

## 1. Project Overview
Enhance the existing Day 6 ShopSphere microservices by introducing Spring Cloud infrastructure and centralized security.

## 2. Existing Platform (Reuse)
- Order Service
- Inventory Service
- Notification Service
- PostgreSQL databases
- Spring WebClient
- RabbitMQ
- JWT Authentication
- RBAC & Authorities
- Logging & Error Handling

## 3. New Components
### Spring Cloud Config Server
- Centralized configuration
- External configuration repository
- Config Clients
- Environment-specific configuration

### Eureka Discovery Server
- Service registration
- Service discovery
- Logical service names
- Eureka Dashboard

### API Gateway
- Single entry point
- Route configuration
- Centralized request routing
- Prevent direct backend access

### Common Security Module
Reusable module containing:
- JWT utilities
- JWT validation
- Authentication filter
- Security configuration
- Authentication entry point
- Authorization helpers
- Shared constants
- Common exception handling

Integrated into:
- API Gateway
- Order Service
- Inventory Service
- Notification Service

## 4. Centralized Security
- Username/Password Authentication
- JWT Generation
- JWT Validation
- RBAC
- Authority-based Authorization
- Method-level Security

## 5. Startup Sequence
1. Config Server
2. Eureka Server
3. API Gateway
4. Order Service
5. Inventory Service
6. Notification Service

## 6. Request Flow
Client
→ API Gateway
→ JWT Validation
→ Route Resolution
→ Eureka Discovery
→ Target Service
→ Business Processing
→ Response

## 7. Validation Criteria
- Config Server operational
- Config Clients connected
- Eureka registration verified
- Gateway routing verified
- Backend inaccessible directly
- JWT validation through Gateway
- RBAC & Authorities enforced
- Common Security Module reused
- Centralized configuration verified

## 8. Deliverables
- Config Server
- Eureka Server
- API Gateway
- Common Security Module
- Updated Order Service
- Updated Inventory Service
- Updated Notification Service
- Documentation
