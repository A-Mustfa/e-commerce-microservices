# E-Commerce Microservices Platform

A comprehensive e-commerce platform built using microservices architecture with Spring Boot and Spring Cloud.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Services](#services)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [API Documentation](#api-documentation)
- [Postman Collection](#postman-collection)
- [Security](#security)
- [Message Queue](#message-queue)
- [Database Schema](#database-schema)

## 🎯 Overview

This is a production-ready e-commerce platform implementing microservices architecture with features including user authentication, product management, shopping cart, order processing, payment handling, and notifications.

## 🏗️ Architecture

The application follows a microservices architecture pattern with the following key components:

```
┌─────────────┐
│ API Gateway │ ← Entry point for all client requests
└──────┬──────┘
       │
┌──────▼───────────────────────────────────────┐
│         Service Discovery (Eureka)           │
└──────────────────┬───────────────────────────┘
                   │
       ┌───────────┼───────────┬─────────────┐
       │           │           │             │
┌──────▼──────┐ ┌─▼────────┐ ┌▼──────────┐ ┌▼─────────────┐
│Auth Service │ │E-Commerce│ │  Payment  │ │ Notification │
│             │ │ Service  │ │  Service  │ │   Service    │
└─────────────┘ └──────────┘ └───────────┘ └──────────────┘
       │             │             │               │
┌──────▼──────┐ ┌───▼──────┐ ┌───▼────┐     ┌────▼────┐
│  Auth DB    │ │E-Comm DB │ │Pay DB  │     │Notif DB │
└─────────────┘ └──────────┘ └────────┘     └─────────┘
```

## 🛠️ Technologies Used

### Core Framework
- **Spring Boot 3.5.8** - Main application framework
- **Java 17** - Programming language
- **Maven** - Dependency management and build tool

### Spring Cloud Components
- **Spring Cloud Config Server** - Centralized configuration management
- **Netflix Eureka** - Service discovery and registration
- **Spring Cloud Gateway** - API Gateway for routing and load balancing
- **OpenFeign** - Declarative REST client for inter-service communication

### Security
- **Spring Security** - Authentication and authorization
- **OAuth2 Resource Server** - Token-based authentication
- **JWT (JSON Web Tokens)** - Stateless authentication mechanism
- **RSA Key Pair** - Public/private key encryption for JWT signing

### Database & Persistence
- **Microsoft SQL Server** - Primary database
- **Spring Data JPA** - Data access layer
- **Hibernate** - ORM framework
- **Liquibase** - Database version control and migration

### Message Queue
- **Apache Kafka** - Event streaming platform
- **Zookeeper** - Kafka coordination service
- **Spring Kafka** - Kafka integration with Spring

### Documentation
- **SpringDoc OpenAPI 3** - API documentation (Swagger UI)

### Additional Tools
- **Lombok** - Reduces boilerplate code
- **MapStruct** - Type-safe bean mapping
- **Jackson** - JSON serialization/deserialization
- **Docker Compose** - Container orchestration for Kafka

## 🔧 Services

### 1. Config Server (Port: 8888)
Centralized configuration management for all microservices.

**Features:**
- Stores configuration in native file system
- Provides environment-specific configurations
- Supports configuration refresh without restart

### 2. Eureka Server (Port: 8761)
Service registry and discovery server.

**Features:**
- Service registration and health monitoring
- Load balancing information
- Service instance discovery

### 3. API Gateway (Port: 8800)
Single entry point for all client requests.

**Features:**
- Request routing to appropriate microservices
- Load balancing
- Cross-cutting concerns (logging, authentication)

**Routes:**
- `/api/v1/cart/**` → E-Commerce Service
- `/api/v1/customer/**` → E-Commerce Service
- `/api/v1/orders/**` → E-Commerce Service
- `/api/v1/item/**` → E-Commerce Service
- `/api/v1/payments/**` → Payment Service
- `/api/v1/auth/**` → Auth Service

### 4. Auth Server (Port: 8004)
Authentication and authorization service.

**Features:**
- User registration and login
- JWT token generation and validation
- RSA-based token signing
- Role-based access control (CUSTOMER, ADMIN)
- User management (suspend users)

**Endpoints:**
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/login` - User login (returns JWT)
- `GET /api/v1/auth/users` - Get all users (Admin only)
- `PATCH /api/v1/auth/postpone/{userId}` - Suspend user (Admin only)

### 5. E-Commerce Service (Port: 8001)
Core business logic for the e-commerce platform.

**Modules:**
- **Customer Management** - User profile management
- **Item Management** - Product catalog
- **Cart Management** - Shopping cart operations
- **Order Management** - Order processing and tracking

**Key Endpoints:**
- `POST /api/v1/customer/create` - Create customer profile
- `GET /api/v1/customer/profile` - Get user profile
- `POST /api/v1/item/add` - Add new item (Admin only)
- `GET /api/v1/item/all` - Get all items
- `POST /api/v1/cart/item` - Add item to cart
- `GET /api/v1/cart/my-cart` - Get user's cart
- `POST /api/v1/orders` - Place order
- `GET /api/v1/orders/all` - Get order history

### 6. Payment Service (Port: 8003)
Handles payment processing and validation.

**Features:**
- Balance validation from JSON mock data
- Payment transaction recording
- Payment status tracking (PAYED, DENIED)

**Endpoints:**
- `POST /api/v1/payments/purchase` - Process payment
- `GET /api/v1/payments` - Get all payments (Admin only)

### 7. Notification Service (Port: 8007)
Asynchronous notification handling via Kafka.

**Features:**
- Consumes order confirmation events
- Stores notification records
- Email notification support (configured but not active)

## 📋 Prerequisites

- **JDK 17** or higher
- **Maven 3.9+**
- **SQL Server** (configured for localhost:1433)
- **Docker & Docker Compose** (for Kafka)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd E-commerce
```

### 2. Database Setup
Create the following databases in SQL Server:
```sql
CREATE DATABASE ecommerce_auth;
CREATE DATABASE ecommerce_service;
CREATE DATABASE ecommerce_payment;
CREATE DATABASE notification_service;
```

Update database credentials in `services/config/src/main/resources/config/*.yml` files:
```yaml
spring:
  datasource:
    username: sa
    password: your_password
```

### 3. Start Kafka
```bash
cd services
docker-compose up -d
```

This starts:
- Zookeeper on port 2181
- Kafka on port 9092

### 4. Start Services in Order

**Step 1: Config Server**
```bash
cd services/config
./mvnw spring-boot:run
```

**Step 2: Eureka Server**
```bash
cd services/register-server
./mvnw spring-boot:run
```

**Step 3: Auth Server**
```bash
cd services/auth-server
./mvnw spring-boot:run
```

**Step 4: Other Services**
```bash
# E-Commerce Service
cd services/ecommerce-service
./mvnw spring-boot:run

# Payment Service
cd services/payment
./mvnw spring-boot:run

# Notification Service
cd services/notification-service
./mvnw spring-boot:run

# API Gateway
cd services/api-gateway
./mvnw spring-boot:run
```

## 📚 API Documentation

Access Swagger UI for each service:
- **Auth Server**: http://localhost:8004/swagger-ui.html
- **E-Commerce Service**: http://localhost:8001/swagger-ui.html
- **Payment Service**: http://localhost:8003/swagger-ui.html
- **Eureka Dashboard**: http://localhost:8761

## Postman Collection

### use the link bellow to access Postman Collection

- [click here to view postman collection](https://amustafa-d6157824-1917882.postman.co/workspace/ahmed-mustafa's-Workspace~595c82fe-0e68-4331-84ff-7253280e569e/collection/50131237-2551c21a-4935-436e-8ff9-3031165f483d?action=share&creator=50131237)
## 🔐 Security

### Authentication Flow

1. **User Registration**
   - User registers via `/api/v1/auth/register`
   - Password is encrypted using BCrypt
   - Customer profile is automatically created

2. **User Login**
   - User logs in via `/api/v1/auth/login` with Basic Auth
   - Server validates credentials
   - Server generates JWT token signed with RSA private key
   - Token contains: userId, email, role, expiration (1 hour)

3. **Accessing Protected Resources**
   - Client includes JWT in Authorization header: `Bearer <token>`
   - API Gateway forwards request to appropriate service
   - Service validates token using RSA public key
   - Service extracts user info from token claims

### JWT Token Structure
```json
{
  "sub": "user@email.com",
  "iss": "self",
  "email": "user@email.com",
  "userId": 5,
  "role": "CUSTOMER",
  "iat": 1640000000,
  "exp": 1640003600
}
```

### Authorization Levels
- **Public**: Registration endpoint
- **Authenticated**: Cart, orders, customer profile
- **Admin Only**: User management, item management, payment history

## 📨 Message Queue

### Kafka Topics

**order-topic**
- **Producer**: E-Commerce Service (when order is placed)
- **Consumer**: Notification Service
- **Message Type**: OrderConfirmation

### Order Confirmation Flow
```
1. User places order
2. E-Commerce Service creates order
3. Payment is processed
4. Order confirmation sent to Kafka
5. Notification Service consumes message
6. Notification record saved to database
```

## 💾 Database Schema

### Auth Database
- **USERS**: User authentication and authorization data

### E-Commerce Database
- **customer**: Customer profile information
- **item**: Product catalog
- **cart**: Shopping carts
- **cart_item**: Items in carts
- **orders**: Order records
- **order_item**: Items in orders

### Payment Database
- **payment**: Payment transaction records

### Notification Database
- **notification**: Notification history

## 🧪 Testing the Application

### 1. Register a User
```bash
curl -X POST http://localhost:8800/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@test.com",
    "password": "password123",
    "role": "CUSTOMER"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8800/api/v1/auth/login \
  -u user@test.com:password123
```

### 3. Use JWT Token
```bash
curl -X GET http://localhost:8800/api/v1/customer/profile \
  -H "Authorization: Bearer <your-jwt-token>"
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Contact

- Email: ahmedazab122@gmail.com
- GitHub: [@A-Mustfa](https://github.com/A-Mustfa)
- Linked-In: [A-mustfa](https://www.linkedin.com/in/a-mustfa)
