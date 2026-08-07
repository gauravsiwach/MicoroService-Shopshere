# ShopSphere Day 7 – Phase-wise Implementation Plan

## Phase 0 – Workspace Preparation
- Use Day 6 workspace
- Verify all services
- Backup project

## Phase 1 – Spring Cloud Config Server
- Create Config Server
- Create centralized configuration repository
- Configure Config Clients
- Verify startup

## Phase 2 – Eureka Discovery Server
- Create Eureka Server
- Register all services
- Verify Eureka Dashboard
- Replace hardcoded URLs

## Phase 3 – API Gateway
- Create Gateway
- Configure routes
- Route Order Service
- Route Inventory Service
- Route Notification Service
- Disable direct backend access

## Phase 4 – Common Security Module
- Create reusable security library
- JWT utilities
- JWT validation
- Authentication filter
- Security configuration
- Shared exception handling
- Shared constants

## Phase 5 – Integrate Security
- Integrate common module with Gateway
- Integrate Order Service
- Integrate Inventory Service
- Integrate Notification Service
- Remove duplicated security

## Phase 6 – Centralized Authentication
- Username/Password login
- JWT generation
- JWT validation
- Populate Security Context
- RBAC
- Authority-based authorization
- Method-level security

## Phase 7 – End-to-End Validation
- Config Server
- Eureka
- Gateway
- JWT
- Routing
- RBAC
- Authorities
- Common Security Module
- Centralized configuration

## Final Submission Checklist
- [ ] Config Server
- [ ] Eureka Server
- [ ] API Gateway
- [ ] Common Security Module
- [ ] Config Clients
- [ ] Service Registration
- [ ] Gateway Routing
- [ ] JWT Authentication
- [ ] RBAC
- [ ] Authorities
- [ ] Method-level Security
- [ ] Centralized Configuration
