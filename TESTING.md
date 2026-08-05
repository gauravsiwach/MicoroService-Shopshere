# ShopSphere Microservices - Testing Guide

This document contains the curl commands for testing the ShopSphere microservices platform.

## Prerequisites

- All three services must be running:
  - Order Service: http://localhost:8084
  - Inventory Service: http://localhost:8085
  - Notification Service: http://localhost:8086
- PostgreSQL running on localhost:5433
- RabbitMQ running on localhost:5672 with username/password: admin/admin

## Test Scenarios

### Step 1: Verify All Services are Running

```bash
# Check Order Service
curl http://localhost:8084/actuator/health

# Check Inventory Service
curl http://localhost:8085/actuator/health

# Check Notification Service
curl http://localhost:8086/actuator/health
```

### Step 2: Create Product in Inventory Service

```bash
curl -X POST http://localhost:8085/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "PROD-001",
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99
  }'
```

### Step 3: Add Stock to Product

```bash
curl -X POST http://localhost:8085/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 2,
    "quantity": 100
  }'
```

### Step 4: Verify Stock

```bash
curl -X POST http://localhost:8085/api/inventory/validate \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "PROD-001",
    "quantity": 5
  }'
```

### Step 5: Create Order (Synchronous Communication Test)

```bash
curl -X POST http://localhost:8084/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productSku": "PROD-001",
    "quantity": 2,
    "totalPrice": 1999.98,
    "customerEmail": "customer@example.com"
  }'
```

### Step 6: Check Stock After Order

```bash
curl -X POST http://localhost:8085/api/inventory/validate \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "PROD-001",
    "quantity": 5
  }'
```

Expected: Available quantity should be reduced by the order quantity.

### Step 7: Verify Notification (Asynchronous Communication Test)

```bash
curl http://localhost:8086/api/notifications/recipient/customer@example.com
```

Expected: Notification created automatically from RabbitMQ event with ORDER_CREATED type.

### Step 8: Cancel Order (Stock Release Test)

```bash
# Get order ID first
curl http://localhost:8084/api/orders

# Cancel the order (replace {order_id} with actual ID)
curl -X PATCH http://localhost:8084/api/orders/{order_id}/cancel
```

### Step 9: Verify Stock After Cancellation

```bash
curl -X POST http://localhost:8085/api/inventory/validate \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "PROD-001",
    "quantity": 5
  }'
```

Expected: Available quantity should increase (stock restored).

### Step 10: Verify Cancellation Notification

```bash
curl http://localhost:8086/api/notifications/recipient/customer@example.com
```

Expected: New notification with ORDER_CANCELLED type.

### Step 11: Test Insufficient Stock Scenario

```bash
curl -X POST http://localhost:8084/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productSku": "PROD-001",
    "quantity": 200,
    "totalPrice": 199998.00,
    "customerEmail": "customer@example.com"
  }'
```

Expected: JSON error response with "Insufficient stock" message.

### Step 12: Test Invalid Product Scenario

```bash
curl -X POST http://localhost:8084/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productSku": "INVALID-SKU",
    "quantity": 1,
    "totalPrice": 99.99,
    "customerEmail": "customer@example.com"
  }'
```

Expected: JSON error response with "Product not found" message.

## Additional Useful Commands

### Get All Orders

```bash
curl http://localhost:8084/api/orders
```

### Get Order by Order Number

```bash
curl http://localhost:8084/api/orders/order-number/ORD-{order_number}
```

### Get All Products

```bash
curl http://localhost:8085/api/products
```

### Get All Inventory

```bash
curl http://localhost:8085/api/inventory
```

### Get All Notifications

```bash
curl http://localhost:8086/api/notifications
```

## Swagger UI

- Order Service: http://localhost:8084/swagger-ui.html
- Inventory Service: http://localhost:8085/swagger-ui.html
- Notification Service: http://localhost:8086/swagger-ui.html
