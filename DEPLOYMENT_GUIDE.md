# ShopSphere Microservices Deployment Guide

This guide provides step-by-step instructions for deploying ShopSphere microservices using Podman.

## Prerequisites

- **Podman**: Container management tool
- **Java 17**: Required for building services
- **Maven**: Build tool for Java applications
- **PostgreSQL**: External database (running on port 5433)
- **RabbitMQ**: External message broker (running on ports 5672, 15672)
- **Memory**: Minimum 4GB allocated to Podman (recommended for running all services)

## Network Setup

Create the Podman network for inter-container communication:

```bash
podman network create shopsphere-network
```

## Configuration Changes

The following configuration changes were made for Docker/Podman networking:

### Config Server
- Switched from Git backend to native file backend
- Configuration repository location: `/app/config-repo`

### Service Configuration
- Changed `localhost` to container names for inter-service communication
- Changed `127.0.0.1` to `host.containers.internal` for external services (PostgreSQL, RabbitMQ)
- Added Eureka client configuration to local `application.properties` for early initialization

## Manual Deployment Steps

### 1. Build Docker Images

```bash
# Build all service images
podman build -t shopsphere/config-server "./config-server "
podman build -t shopsphere/eureka-server ./DiscoverServer
podman build -t shopsphere/api-gateway ./ApiGateway
podman build -t shopsphere/order-service ./order-service
podman build -t shopsphere/inventory-service ./inventory-service
podman build -t shopsphere/notification-service ./notification-service
```

### 2. Deploy Infrastructure Services

```bash
# Deploy Config Server
podman run -d --name config-server --network shopsphere-network -p 8888:8888 shopsphere/config-server

# Deploy Eureka Server
podman run -d --name eureka-server --network shopsphere-network -p 8761:8761 shopsphere/eureka-server
```

### 3. Deploy Application Services

```bash
# Deploy API Gateway
podman run -d --name api-gateway --network shopsphere-network -p 8080:8080 shopsphere/api-gateway

# Deploy Order Service (with 1GB memory limit)
podman run -d --name order-service --network shopsphere-network -p 8084:8084 --memory=1g shopsphere/order-service

# Deploy Inventory Service (with 1GB memory limit)
podman run -d --name inventory-service --network shopsphere-network -p 8085:8085 --memory=1g shopsphere/inventory-service

# Deploy Notification Service
podman run -d --name notification-service --network shopsphere-network -p 8086:8086 shopsphere/notification-service
```

### 4. Verify Deployment

```bash
# Check running containers
podman ps

# Check service logs
podman logs config-server
podman logs eureka-server
podman logs api-gateway
podman logs order-service
podman logs inventory-service
podman logs notification-service

# Verify Eureka registration
curl http://localhost:8761/
```

## Service Endpoints

- **Config Server**: http://localhost:8888
- **Eureka Server**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Order Service**: http://localhost:8084
- **Inventory Service**: http://localhost:8085
- **Notification Service**: http://localhost:8086

## API Gateway Routes

- `/api/orders/**` → Order Service
- `/api/products/**` → Inventory Service
- `/api/inventory/**` → Inventory Service
- `/api/notifications/**` → Notification Service

## Authentication

JWT authentication is available through the API Gateway:

```bash
# Login and get JWT token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Use token for authenticated requests
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/orders
```

## Troubleshooting

### Memory Issues
If services are being killed (exit code 137), increase Podman memory allocation:
- Check current allocation: `podman info`
- Increase to minimum 4GB for all services to run simultaneously

### Service Registration Issues
If services don't register with Eureka:
- Check Eureka server is running: `curl http://localhost:8761/`
- Verify network connectivity: `podman network inspect shopsphere-network`
- Check service logs for connection errors

### Database Connection Issues
If services can't connect to PostgreSQL:
- Verify PostgreSQL is running on port 5433
- Check `host.containers.internal` is used in configuration
- Test connectivity: `podman run --rm alpine ping host.containers.internal`

### Configuration Server Issues
If services can't fetch configuration:
- Verify config-server is running on port 8888
- Check config-repo directory exists in container
- Test configuration endpoint: `curl http://localhost:8888/config-server/default`

## Stopping Services

```bash
# Stop all services
podman stop config-server eureka-server api-gateway order-service inventory-service notification-service

# Remove containers
podman rm config-server eureka-server api-gateway order-service inventory-service notification-service
```

## Using podman-compose

For automated deployment, use the provided `docker-compose.yml` file:

```bash
# Build and start all services
podman-compose up -d

# View logs
podman-compose logs -f

# Stop services
podman-compose down
```

## Important Notes

- **Memory Requirements**: Order-service and inventory-service require 1GB each to prevent OOM kills
- **Network**: All services must be on the `shopsphere-network` for proper communication
- **Startup Order**: Services depend on config-server and eureka-server being available first
- **External Services**: PostgreSQL and RabbitMQ must be running before starting application services
- **Configuration**: Changes to configuration files require rebuilding the config-server image
