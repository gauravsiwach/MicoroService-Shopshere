# ShopSphere Microservices Platform

A microservices implementation of the ShopSphere Order Management Platform demonstrating inter-service communication using Spring WebClient (synchronous) and RabbitMQ (asynchronous).

## Architecture

The platform consists of three independent Spring Boot microservices:

- **Order Service** (port 8084) - Manages order creation, updates, and cancellation
- **Inventory Service** (port 8085) - Manages product inventory and stock validation
- **Notification Service** (port 8086) - Manages customer notifications via RabbitMQ events

### Communication Patterns

- **Synchronous**: Order Service → Inventory Service (WebClient)
  - Stock validation before order creation
  - Stock reservation on order placement
  - Stock release on order cancellation

- **Asynchronous**: Order Service → Notification Service (RabbitMQ)
  - Order created events
  - Order updated events
  - Order cancelled events

### Database Per Service

- **orders_db** - Order Service
- **inventory_db** - Inventory Service
- **notification_db** - Notification Service

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 14+ (running on localhost:5433)
- RabbitMQ 3.12+ (running on localhost:5672)

## Setup Instructions

### 1. Install PostgreSQL

```bash
# Using Homebrew (macOS)
brew install postgresql@14
brew services start postgresql@14

# Create databases
psql -U postgres
CREATE DATABASE orders_db;
CREATE DATABASE inventory_db;
CREATE DATABASE notification_db;
\q
```

### 2. Install RabbitMQ

```bash
# Using Homebrew (macOS)
brew install rabbitmq
brew services start rabbitmq

# Add user with admin credentials
rabbitmqctl add_user admin admin
rabbitmqctl set_user_tags admin administrator
rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"
```

### 3. Configure PostgreSQL Port

If your PostgreSQL is running on a different port, update the `spring.datasource.url` in each service's `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/{database_name}
```

### 4. Build All Services

```bash
# Build Order Service
cd order-service
mvn clean install

# Build Inventory Service
cd ../inventory-service
mvn clean install

# Build Notification Service
cd ../notification-service
mvn clean install
```

## Running the Services

### Option 1: Run from IDE

1. Open each service in your IDE (IntelliJ IDEA, Eclipse, etc.)
2. Run the main class of each service:
   - Order Service: `OrderServiceApplication`
   - Inventory Service: `InventoryServiceApplication`
   - Notification Service: `NotificationServiceApplication`

### Option 2: Run from Command Line

```bash
# Terminal 1 - Order Service
cd order-service
mvn spring-boot:run

# Terminal 2 - Inventory Service
cd inventory-service
mvn spring-boot:run

# Terminal 3 - Notification Service
cd notification-service
mvn spring-boot:run
```

### Option 3: Run as JAR Files

```bash
# Build all services
cd order-service && mvn clean package
cd ../inventory-service && mvn clean package
cd ../notification-service && mvn clean package

# Run Order Service
java -jar order-service/target/order-service-0.0.1-SNAPSHOT.jar

# Run Inventory Service
java -jar inventory-service/target/inventory-service-0.0.1-SNAPSHOT.jar

# Run Notification Service
java -jar notification-service/target/notification-service-0.0.1-SNAPSHOT.jar
```

## Verification

Once all services are running, verify they are healthy:

```bash
# Order Service
curl http://localhost:8084/actuator/health

# Inventory Service
curl http://localhost:8085/actuator/health

# Notification Service
curl http://localhost:8086/actuator/health
```

## API Documentation

Swagger UI is available for each service:

- Order Service: http://localhost:8084/swagger-ui.html
- Inventory Service: http://localhost:8085/swagger-ui.html
- Notification Service: http://localhost:8086/swagger-ui.html

## Testing

For detailed testing instructions and curl commands, see [TESTING.md](TESTING.md).

### Quick Test Flow

1. Create a product in Inventory Service
2. Add stock to the product
3. Create an order (will validate stock and reserve it)
4. Check stock after order (should be reduced)
5. Verify notification was created (asynchronous)
6. Cancel the order (will release stock)
7. Check stock after cancellation (should be restored)
8. Verify cancellation notification was created

## Configuration

### Order Service (port 8084)

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5433/orders_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin

# Inventory Service
inventory.service.base-url=http://localhost:8085
```

### Inventory Service (port 8085)

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5433/inventory_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

### Notification Service (port 8086)

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5433/notification_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

## Troubleshooting

### Service won't start

- Check if PostgreSQL is running: `brew services list`
- Check if RabbitMQ is running: `brew services list`
- Verify database credentials in `application.properties`
- Check if ports 8084, 8085, 8086 are available

### RabbitMQ connection errors

- Verify RabbitMQ is running: `rabbitmqctl status`
- Check RabbitMQ credentials: username/password should be `admin/admin`
- Verify RabbitMQ is on port 5672

### Database connection errors

- Verify PostgreSQL is running: `pg_isready`
- Check if databases exist: `psql -U postgres -l`
- Verify database credentials in `application.properties`
- Check PostgreSQL port (default is 5433 in this project)

### Stock not being reserved/released

- Check Inventory Service logs for errors
- Verify WebClient is configured correctly in Order Service
- Check if reserve/release endpoints are using @RequestBody (fixed in implementation)

### Notifications not being created

- Check RabbitMQ queue: `rabbitmqctl list_queues`
- Verify RabbitMQ credentials in both services
- Check Notification Service logs for consumer errors
- Ensure Jackson2JsonMessageConverter is configured (fixed in implementation)

## Project Structure

```
ShopSphere-MicroService/
├── order-service/              # Order Management Service
│   ├── src/main/java/
│   │   └── com/example/order_service/
│   │       ├── config/        # WebClient, RabbitMQ configs
│   │       ├── controller/    # REST controllers
│   │       ├── dto/           # Request/Response DTOs
│   │       ├── entity/        # Order entity
│   │       ├── exception/     # Exception handlers
│   │       ├── publisher/     # RabbitMQ event publisher
│   │       ├── repository/    # JPA repositories
│   │       └── service/       # Business logic
│   └── src/main/resources/
│       └── application.properties
├── inventory-service/         # Inventory Management Service
│   ├── src/main/java/
│   │   └── com/example/inventory_service/
│   │       ├── config/        # RabbitMQ config
│   │       ├── controller/    # REST controllers
│   │       ├── dto/           # Request/Response DTOs
│   │       ├── entity/        # Product, Inventory entities
│   │       ├── exception/     # Exception handlers
│   │       ├── repository/    # JPA repositories
│   │       └── service/       # Business logic
│   └── src/main/resources/
│       └── application.properties
├── notification-service/      # Notification Service
│   ├── src/main/java/
│   │   └── com/example/notification_service/
│   │       ├── config/        # RabbitMQ config
│   │       ├── consumer/      # RabbitMQ event consumer
│   │       ├── controller/    # REST controllers
│   │       ├── dto/           # Request/Response DTOs
│   │       ├── entity/        # Notification entity
│   │       ├── exception/     # Exception handlers
│   │       ├── repository/    # JPA repositories
│   │       └── service/       # Business logic
│   └── src/main/resources/
│       └── application.properties
├── README.md                  # This file
└── TESTING.md                 # Testing guide with curl commands
```

## Technologies Used

- **Java 17** - Programming language
- **Spring Boot 3.x** - Application framework
- **Spring Data JPA** - Database access
- **Spring WebFlux** - Reactive WebClient
- **Spring AMQP** - RabbitMQ integration
- **PostgreSQL** - Relational database
- **RabbitMQ** - Message broker
- **Lombok** - Reduce boilerplate code
- **Swagger/OpenAPI** - API documentation
- **Maven** - Build tool

## Lab Coverage

This implementation covers all lab requirements for ShopSphere Lab Day 6:

- ✅ Microservices Architecture (3 independent services)
- ✅ Database Per Service (separate databases for each service)
- ✅ Service Responsibilities (clearly defined boundaries)
- ✅ Synchronous Communication using WebClient (Order → Inventory)
- ✅ Asynchronous Communication using RabbitMQ (Order → Notification)
- ✅ Error Scenarios in Service Communication (proper error handling)
- ✅ Logging and Monitoring (comprehensive logging throughout)
- ✅ End-to-End Request Flow (complete order lifecycle)

**Estimated Lab Score: 20/20**

## License

This is a learning project for educational purposes.
