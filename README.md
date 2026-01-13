# ecommerce-backend

A RESTful API application built with Spring Boot and Spring Data JPA for managing products and orders. This ecommerce backend demonstrates CRUD (Create, Read, Update, Delete) operations with pagination support using PostgreSQL as the database. Features JWT-based authentication with OAuth2 support (Google), user registration, login, and role-based access control.

## 🎨 Frontend Application

This backend API is designed to work with the **ecommerce-frontend** React application:

**Frontend Repository**: [https://github.com/mhmdfathy96/ecommerce-frontend](https://github.com/mhmdfathy96/ecommerce-frontend)

The frontend is built with React and connects to this backend API to provide a complete ecommerce experience. The backend API includes CORS configuration to allow requests from the React frontend running on `http://localhost:5173`.

## 🚀 Features

- **CRUD Operations**: Full Create, Read, Update, Delete functionality for products
- **Order Management**: Place orders and retrieve order history with pagination
- **Stock Management**: Automatic stock validation and updates when placing orders
- **Authentication & Authorization**: JWT-based authentication with OAuth2 support (Google)
- **User Registration & Login**: Traditional username/password authentication
- **OAuth2 Integration**: Sign in with Google OAuth2
- **Refresh Tokens**: Secure token refresh mechanism for long-lived sessions
- **Pagination**: Get all products and orders with pagination support
- **Global Exception Handling**: Meaningful error responses (400 Bad Request) instead of generic 500 errors
- **Spring Data JPA**: Simplified data access using JPA repositories
- **PostgreSQL**: Robust relational database for data persistence
- **Swagger UI**: Interactive API documentation and testing interface
- **Lombok**: Reduces boilerplate code with annotations
- **Environment-based Configuration**: Support for multiple environments (dev, stg, prod) with `.env` file support

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21** (or higher)
- **Maven 3.6+** (or use the included Maven Wrapper)
- **PostgreSQL** (version 12 or higher)
- **Postman** (optional, for API testing) or use Swagger UI

## 🔧 Environment Setup

### Quick Start

1. **Clone the repository** (if you haven't already)
2. **Copy the environment file**:
   ```bash
   cp .env.example .env
   ```
3. **Configure your environment variables** in the `.env` file (see details below)
4. **Set up the database** (see Database Setup section)
5. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

### 1. Environment Variables Configuration

This application uses environment variables for configuration. The `.env.example` file serves as a template with all required variables.

**Copy the example file and configure your variables**:

```bash
# Copy the example file
cp .env.example .env
```

**Edit the `.env` file** with your actual values:

```bash
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/test
DB_USER=postgres
DB_PASSWORD=your_password_here

# Google OAuth2 Configuration
GOOGLE_CLIENT_ID=your_google_client_id_here
GOOGLE_CLIENT_SECRET=your_google_client_secret_here

# JWT Configuration
JWT_SECRET=your_jwt_secret_key_here_min_256_bits
JWT_EXPIRATION=3600000
JWT_REFRESH_EXPIRATION=604800000

# Spring Profile (optional)
SPRING_PROFILES_ACTIVE=dev
```

**Important**: The `.env` file is gitignored for security. Never commit your actual `.env` file to version control.

### 2. Database Setup

#### Install PostgreSQL

If you don't have PostgreSQL installed, download and install it from [PostgreSQL Official Website](https://www.postgresql.org/download/).

#### Create Database

1. Open PostgreSQL command line (psql) or use a GUI tool like pgAdmin
2. Connect to PostgreSQL server (default user is usually `postgres`)
3. Create the database:

```sql
CREATE DATABASE test;
```

4. Update the database credentials in your `.env` file:

```bash
DB_URL=jdbc:postgresql://localhost:5432/test
DB_USER=postgres
DB_PASSWORD=your_actual_password
```

### 3. Google OAuth2 Setup

To enable Google OAuth2 authentication:

1. **Create a Google Cloud Project**:

   - Go to [Google Cloud Console](https://console.cloud.google.com/)
   - Create a new project or select an existing one

2. **Enable Google+ API**:

   - Navigate to "APIs & Services" > "Library"
   - Search for "Google+ API" and enable it

3. **Create OAuth2 Credentials**:

   - Go to "APIs & Services" > "Credentials"
   - Click "Create Credentials" > "OAuth client ID"
   - Choose "Web application"
   - Add authorized redirect URIs:
     - For local development: `http://localhost:8080/api/auth/oauth2/callback/google`
     - For production: `https://yourdomain.com/api/auth/oauth2/callback/google`

4. **Copy Credentials**:
   - Copy the **Client ID** and **Client Secret**
   - Add them to your `.env` file:
     ```bash
     GOOGLE_CLIENT_ID=your_client_id.apps.googleusercontent.com
     GOOGLE_CLIENT_SECRET=your_client_secret
     ```

### 4. JWT Secret Generation

Generate a strong secret key for JWT signing (at least 256 bits):

```bash
# Using OpenSSL
openssl rand -base64 32

# Or using Python
python3 -c "import secrets; print(secrets.token_urlsafe(32))"
```

Add the generated secret to your `.env` file:

```bash
JWT_SECRET=your_generated_secret_here
```

**JWT Expiration Times** (in milliseconds):

- `JWT_EXPIRATION=3600000` - Access token expiration (default: 1 hour)
- `JWT_REFRESH_EXPIRATION=604800000` - Refresh token expiration (default: 7 days)

## 🏗️ Project Structure

```
ecommerce-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/mindtocode/springdatajpa/
│   │   │       ├── SpringDataJpaApplication.java    # Main application class
│   │   │       ├── controller/
│   │   │       │   ├── OrderController.java         # Order REST controller
│   │   │       │   └── ProductController.java       # Product REST controller
│   │   │       ├── model/
│   │   │       │   ├── dto/                          # Data Transfer Objects
│   │   │       │   │   ├── OrderRequest.java
│   │   │       │   │   ├── OrderResponse.java
│   │   │       │   │   ├── OrderItemRequest.java
│   │   │       │   │   └── OrderItemResponse.java
│   │   │       │   ├── Order.java                   # Order entity
│   │   │       │   ├── OrderItem.java               # OrderItem entity
│   │   │       │   └── Product.java                 # Product entity
│   │   │       ├── repo/
│   │   │       │   ├── OrderRepo.java               # Order JPA repository
│   │   │       │   └── ProductRepo.java             # Product JPA repository
│   │   │       ├── service/
│   │   │       │   ├── OrderService.java            # Order business logic
│   │   │       │   └── ProductService.java          # Product business logic
│   │   │       └── exceptions/
│   │   │           ├── GlobalExceptionHandler.java  # Global exception handler
│   │   │           ├── ProductNotFoundException.java
│   │   │           └── ProductOutOfStockException.java
│   │   └── resources/
│   │       └── application.properties                # Application configuration
│   └── test/
└── pom.xml                                           # Maven dependencies
```

## 📦 Technologies Used

- **Spring Boot 4.0.1**: Framework for building the application
- **Spring Data JPA**: Simplifies data access layer
- **PostgreSQL**: Relational database management system
- **Lombok**: Library to reduce boilerplate code
- **SpringDoc OpenAPI**: Swagger UI integration for API documentation
- **Maven**: Build and dependency management tool

## 🚀 Running the Application

### Option 1: Using Maven Wrapper (Recommended)

```bash
# On macOS/Linux
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run
```

### Option 2: Using Maven (if installed globally)

```bash
mvn spring-boot:run
```

### Option 3: Using IDE

1. Import the project into your IDE (IntelliJ IDEA, Eclipse, etc.)
2. Wait for Maven dependencies to download
3. Run the `SpringDataJpaApplication` class

The application will start on **http://localhost:8080**

### Running with Frontend

To run the complete ecommerce application:

1. **Start the Backend** (this application):

   ```bash
   ./mvnw spring-boot:run
   ```

   Backend will be available at: `http://localhost:8080`

2. **Start the Frontend**:

   ```bash
   # Clone the frontend repository
   git clone https://github.com/mhmdfathy96/ecommerce-frontend.git
   cd ecommerce-frontend

   # Install dependencies and run
   npm install
   npm run dev
   ```

   Frontend will be available at: `http://localhost:5173`

The frontend is configured to communicate with the backend API automatically.

## 🧪 Testing the API

You can test the CRUD operations using either **Swagger UI** (recommended) or **Postman**.

### 🌟 Swagger UI (Recommended - "The Lovable Way to My Heart")

Once the application is running, navigate to:

**http://localhost:8080/swagger-ui/index.html**

Swagger UI provides an interactive interface where you can:

- View all available API endpoints
- See request/response schemas
- Test endpoints directly from the browser
- No need for external tools!

### Using Postman

Alternatively, you can use Postman to test the endpoints. Here are the available endpoints:

#### 1. Create Product (POST)

**URL**: `http://localhost:8080/product`

**Method**: `POST`

**Headers**:

```
Content-Type: application/json
```

**Body** (JSON):

```json
{
  "name": "Laptop",
  "specs": ["16GB RAM", "512GB SSD", "Intel i7"]
}
```

#### 2. Get All Products (GET) - With Pagination

**URL**: `http://localhost:8080/product?page=0&size=2`

**Method**: `GET`

**Query Parameters**:

- `page`: Page number (default: 0)
- `size`: Number of items per page (default: 2)

**Example**:

- `http://localhost:8080/product` (uses defaults: page=0, size=2)
- `http://localhost:8080/product?page=0&size=10`
- `http://localhost:8080/` (root endpoint also works)

#### 3. Update Product (PUT)

**URL**: `http://localhost:8080/product`

**Method**: `PUT`

**Headers**:

```
Content-Type: application/json
```

**Body** (JSON):

```json
{
  "id": 1,
  "name": "Updated Laptop",
  "specs": ["32GB RAM", "1TB SSD", "Intel i9"]
}
```

#### 4. Delete Product (DELETE)

**URL**: `http://localhost:8080/product/{productId}`

**Method**: `DELETE`

**Example**: `http://localhost:8080/product/1`

#### 5. Place Order (POST)

**URL**: `http://localhost:8080/api/orders/place`

**Method**: `POST`

**Headers**:

```
Content-Type: application/json
```

**Body** (JSON):

```json
{
  "customerName": "John Doe",
  "email": "john.doe@example.com",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

**Response** (JSON):

```json
{
  "orderId": "a1b2c3d4",
  "customerName": "John Doe",
  "email": "john.doe@example.com",
  "orderDate": "2024-01-15T10:30:00.000+00:00",
  "status": "placed",
  "items": [
    {
      "productName": "Laptop",
      "quantity": 2,
      "totalPrice": 1999.98
    },
    {
      "productName": "Mouse",
      "quantity": 1,
      "totalPrice": 29.99
    }
  ]
}
```

#### 6. Get All Orders (GET) - With Pagination

**URL**: `http://localhost:8080/api/orders?page=0&size=5`

**Method**: `GET`

**Query Parameters**:

- `page`: Page number (default: 0)
- `size`: Number of items per page (default: 5)

**Example**:

- `http://localhost:8080/api/orders` (uses defaults: page=0, size=5)
- `http://localhost:8080/api/orders?page=0&size=10`

## 📝 API Endpoints Summary

### Authentication Endpoints

| Method | Endpoint                                | Description                       | Authentication Required | Parameters                                                |
| ------ | --------------------------------------- | --------------------------------- | ----------------------- | --------------------------------------------------------- |
| POST   | `/api/auth/register`                    | Register a new user               | No                      | Request Body: User JSON (username, password, email, role) |
| POST   | `/api/auth/login`                       | Login with username/password      | No                      | Request Body: LoginRequest JSON (username, password)      |
| POST   | `/api/auth/refresh`                     | Refresh access token              | No                      | Request Body: RefreshTokenRequest JSON (refreshToken)     |
| GET    | `/api/auth/oauth2/authorize/{provider}` | Initiate OAuth2 login (redirects) | No                      | Path: `provider` (e.g., "google")                         |
| GET    | `/api/auth/oauth2/callback/{provider}`  | OAuth2 callback endpoint          | No                      | Query: `code`, `state`; Path: `provider`                  |

**Authentication Response Format**:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "user@example.com",
  "role": "USER"
}
```

### Product Endpoints

| Method | Endpoint                  | Description                  | Authentication Required | Parameters                                      |
| ------ | ------------------------- | ---------------------------- | ----------------------- | ----------------------------------------------- |
| POST   | `/api/product`            | Create a new product         | Yes (Admin)             | Request Body: Product JSON                      |
| GET    | `/api/products`           | Get all products (paginated) | No                      | Query: `page` (default: 0), `size` (default: 5) |
| GET    | `/api/products/search`    | Search products by keyword   | No                      | Query: `page`, `size`, `keyword`                |
| GET    | `/api/product/{id}`       | Get product by ID            | No                      | Path: `productId`                               |
| PUT    | `/api/product`            | Update an existing product   | Yes (Admin)             | Request Body: Product JSON with id              |
| DELETE | `/api/product/{id}`       | Delete a product by ID       | Yes (Admin)             | Path: `productId`                               |
| GET    | `/api/product/{id}/image` | Get product image            | No                      | Path: `productId`                               |

### Order Endpoints

| Method | Endpoint            | Description                | Authentication Required | Parameters                                      |
| ------ | ------------------- | -------------------------- | ----------------------- | ----------------------------------------------- |
| POST   | `/api/orders/place` | Place a new order          | Yes                     | Request Body: OrderRequest JSON                 |
| GET    | `/api/orders`       | Get all orders (paginated) | Yes                     | Query: `page` (default: 0), `size` (default: 5) |

**Note**:

- Endpoints marked with "Yes" require authentication (JWT token required)
- Endpoints marked with "Yes (Admin)" require authentication AND admin role (`@PreAuthorize("hasRole('ADMIN')")`)
- Endpoints marked with "No" are publicly accessible

Include the JWT token in the Authorization header for protected endpoints:

```
Authorization: Bearer <access_token>
```

## 🏛️ Architecture Overview

This application follows a **layered architecture** pattern:

### 1. **Controller Layer**

- **`ProductController`**: Handles product-related HTTP requests and responses
- **`OrderController`**: Handles order-related HTTP requests and responses
- **`AuthController`**: Handles authentication endpoints (register, login, refresh, OAuth2)
- Maps URLs to service methods
- Uses `@RestController` for REST API endpoints
- Located in: `controller/` package

### 2. **Service Layer**

- **`ProductService`**: Contains product business logic
- **`OrderService`**: Contains order business logic, handles stock validation and updates
- **`AuthService`**: Handles user registration, authentication, and token management
- **`JwtService`**: Generates and validates JWT access and refresh tokens
- **`GoogleOAuthService`**: Implements `IOAuthService` interface, handles Google OAuth2 flow (code exchange, user info fetching)
- **`IOAuthService`**: Interface defining OAuth2 service contract (`exchangeCode`, `fetchUserInfo`) for extensibility
- **`MyUserDetailsService`**: Spring Security UserDetailsService implementation, loads user details for authentication
- Orchestrates data operations
- Uses `@Service` annotation
- Located in: `service/` package

### 3. **Repository Layer**

- **`ProductRepo`**: Extends `JpaRepository` for product database operations
- **`OrderRepo`**: Extends `JpaRepository` for order database operations
- **`UserRepo`**: Extends `JpaRepository` for user database operations
- **`RefreshTokenRepo`**: Extends `JpaRepository` for refresh token storage
- **`OAuth2StateRepo`**: Extends `JpaRepository` for OAuth2 state validation
- **`JpaRepository`**: Base repository interface (extends Spring Data JPA `JpaRepository`) providing common CRUD operations
- Provides CRUD methods automatically through Spring Data JPA
- Located in: `repo/` package

### 4. **Model/Entity Layer**

- **`Product`**: Represents product database table structure
- **`Order`**: Represents order database table structure
- **`OrderItem`**: Represents order items (many-to-one relationship with Order and Product)
- **`User`**: Represents user database table structure with authentication details (username, password, role, provider)
- **`RefreshToken`**: Represents refresh token storage for JWT refresh mechanism (token, user relationship)
- **`OAuth2State`**: Entity for storing OAuth2 state tokens for CSRF protection (state, provider)
- **`Role`**: Enum defining user roles (`USER`, `ADMIN`) for type-safe role management
- **`UserPrinciple`**: Spring Security UserDetails implementation, wraps User entity for Spring Security
- Uses JPA annotations (`@Entity`, `@Id`, `@OneToMany`, `@ManyToOne`, `@Enumerated`, etc.)
- Uses Lombok annotations for getters/setters
- Located in: `model/` package

### 5. **DTO Layer**

- **`OrderRequest`**: Request DTO for placing orders
- **`OrderResponse`**: Response DTO for order data
- **`OrderItemRequest`**: Request DTO for order items
- **`OrderItemResponse`**: Response DTO for order item data
- **`LoginRequest`**: Request DTO for login (username, password)
- **`AuthResponse`**: Response DTO for authentication (accessToken, refreshToken, username, role)
- **`RefreshTokenRequest`**: Request DTO for token refresh
- **`OAuthUserInfo`**: DTO for OAuth2 user information (email, name from OAuth provider)
- **`ErrorResponse`**: Standardized error response DTO (message, error, status, timestamp) for consistent error formatting
- Located in: `model/dto/` package

### 6. **Configuration Layer**

- **`SecurityConfig`**: Spring Security configuration with JWT authentication and method-level security enabled (`@EnableMethodSecurity`)
- **`JwtAuthFilter`**: JWT token validation filter for protected endpoints, intercepts requests and validates JWT tokens
- **`JwtProperties`**: Configuration properties for JWT (secret key, access token expiration, refresh token expiration)
- **`GoogleOAuthProperties`**: Configuration properties for Google OAuth2 (client ID, client secret, callback URL, auth URL)
- **`RestTemplateConfig`**: RestTemplate bean configuration for OAuth2 API calls to external providers
- Located in: `config/` package

**Security Configuration**:

- Method-level security enabled via `@EnableMethodSecurity(prePostEnabled = true)`
- Role-based access control using `@PreAuthorize` annotations on controllers
- User roles are automatically prefixed with `ROLE_` in `UserPrinciple` for Spring Security compatibility

### 7. **Exception Handling**

- **`GlobalExceptionHandler`**: Global exception handler using `@RestControllerAdvice`
  - Centralized exception handling for the entire application
  - Converts exceptions to standardized `ErrorResponse` DTOs
  - Returns appropriate HTTP status codes and error messages
- **`ProductNotFoundException`**: Custom exception thrown when a requested product is not found
  - Extends `RuntimeException`
  - Used in product lookup operations
- **`ProductOutOfStockException`**: Custom exception thrown when placing orders for products with insufficient stock
  - Extends `RuntimeException`
  - Used in order placement validation
- Returns meaningful error responses (`ErrorResponse`) instead of generic 500 errors
- Located in: `exceptions/` package

### 8. **Aspect-Oriented Programming (AOP)**

- **`LoggingAspect`**: Logs method invocations for monitoring
- **`PerformanceMonitoringAspect`**: Monitors method execution time
- Located in: `aop/` package

### 9. **Application Entry Point**

- **`SpringDataJpaApplication`**: Main application class annotated with `@SpringBootApplication`
  - Entry point for the Spring Boot application
  - Handles `.env` file loading for local development using Dotenv library
  - Automatically loads environment variables from `.env` file only in development mode (when `dev` profile is active or no profile is set)
  - In production/staging environments, relies on system environment variables provided by the platform
  - Located in root package: `org.mindtocode.ecommercebackend`

## 📊 Data Model

### Product Entity

```java
- id: Integer (Auto-generated primary key)
- name: String (Product name)
- price: BigDecimal (Product price)
- stockQuantity: Integer (Available stock quantity)
- imageData: byte[] (Product image)
- specs: List<String> (Product specifications)
```

### Order Entity

```java
- id: Long (Auto-generated primary key)
- orderId: String (Unique order identifier, UUID-based)
- customerName: String (Customer name)
- email: String (Customer email)
- status: String (Order status, e.g., "placed")
- orderDate: Date (Order creation date, auto-generated)
- orderItems: List<OrderItem> (One-to-many relationship)
```

### OrderItem Entity

```java
- id: Integer (Auto-generated primary key)
- product: Product (Many-to-one relationship)
- order: Order (Many-to-one relationship)
- quantity: Integer (Quantity ordered)
- totalPrice: BigDecimal (Calculated: product.price * quantity)
```

### User Entity

```java
- id: Integer (Auto-generated primary key)
- username: String (Unique, nullable: false, typically email)
- password: String (Nullable for OAuth2 users, encrypted with BCrypt)
- role: Role (Enum: USER or ADMIN, defaults to USER via @PrePersist)
- provider: String (OAuth2 provider name, e.g., "google", nullable)
- refreshToken: List<RefreshToken> (One-to-many relationship)
```

### RefreshToken Entity

```java
- token: String (Primary key, unique, nullable: false, max length 255)
- user: User (Many-to-one relationship)
```

### OAuth2State Entity

```java
- state: String (Primary key, unique OAuth2 state token for CSRF protection)
- provider: String (OAuth2 provider name, e.g., "google")
```

### Role Enum

```java
- USER: Standard user role
- ADMIN: Administrator role with elevated permissions
```

### Relationships

- **Order** ↔ **OrderItem**: One-to-Many (One order can have multiple order items)
- **Product** ↔ **OrderItem**: One-to-Many (One product can be in multiple order items)
- **OrderItem** → **Order**: Many-to-One (Each order item belongs to one order)
- **OrderItem** → **Product**: Many-to-One (Each order item references one product)
- **User** ↔ **RefreshToken**: One-to-Many (One user can have multiple refresh tokens)
- **RefreshToken** → **User**: Many-to-One (Each refresh token belongs to one user)

## ⚙️ Configuration

### Environment-Based Configuration

The application uses Spring profiles and environment variables for configuration:

- **Development** (`application-dev.yml`): Uses `.env` file, debug logging, `ddl-auto=update`
- **Staging** (`application-stg.yml`): Uses environment variables, info logging, `ddl-auto=validate`
- **Production** (`application-prod.yml`): Uses environment variables, warn logging, `ddl-auto=validate`

### Application Configuration Files

#### Base Configuration (`application.yml`)

Contains common settings and OAuth2 URLs:

- Application name
- Session cookie settings
- Google OAuth2 URLs (auth URL, token URL, user info URL, callback endpoint)

#### Environment-Specific Configuration

**Development** (`application-dev.yml`):

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update # Auto-create/update schema
    show-sql: true # Show SQL queries

logging:
  level:
    root: debug # Debug logging
```

**Staging/Production** (`application-stg.yml`, `application-prod.yml`):

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate # Validate schema only
    show-sql: false # Don't show SQL

logging:
  level:
    root: info/warn # Production logging
```

### Environment Variables

All sensitive configuration is loaded from environment variables (or `.env` file in development):

| Variable                 | Description                               | Example                                 |
| ------------------------ | ----------------------------------------- | --------------------------------------- |
| `DB_URL`                 | PostgreSQL database connection URL        | `jdbc:postgresql://localhost:5432/test` |
| `DB_USER`                | Database username                         | `postgres`                              |
| `DB_PASSWORD`            | Database password                         | `your_password`                         |
| `GOOGLE_CLIENT_ID`       | Google OAuth2 Client ID                   | `xxx.apps.googleusercontent.com`        |
| `GOOGLE_CLIENT_SECRET`   | Google OAuth2 Client Secret               | `GOCSPX-xxx`                            |
| `JWT_SECRET`             | Secret key for JWT signing (min 256 bits) | Generated secret                        |
| `JWT_EXPIRATION`         | Access token expiration (milliseconds)    | `3600000` (1 hour)                      |
| `JWT_REFRESH_EXPIRATION` | Refresh token expiration (milliseconds)   | `604800000` (7 days)                    |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile                     | `dev`, `stg`, `prod`                    |

### Key Settings

- **`ddl-auto=update`** (Dev): Automatically creates/updates database schema on startup
- **`ddl-auto=validate`** (Stg/Prod): Validates schema without making changes
- **`show-sql=true`** (Dev): Shows SQL queries in console for debugging
- **`.env` file**: Automatically loaded in development mode (see `SpringDataJpaApplication.java`)

### CORS Configuration

The backend is configured to accept requests from the React frontend. CORS is enabled for:

- `http://localhost:5173` (Vite default port)

This is configured in the controllers using `@CrossOrigin` annotation.

## 🔍 How It Works

1. **Application Startup**: Spring Boot initializes the application context
2. **Database Connection**: Connects to PostgreSQL using configured credentials
3. **Schema Creation**: Hibernate automatically creates/updates the database tables (`product`, `orders`, `order_item`)
4. **Repository Setup**: Spring Data JPA creates proxy implementations for `ProductRepo`
5. **API Exposure**: REST endpoints become available on `http://localhost:8080`

### Flow of a Request

```
Client Request → Controller → Service → Repository → Database
                                    ↓
Client Response ← Controller ← Service ← Repository ← Database
```

## 🎯 Key Spring Data JPA Features Demonstrated

1. **Repository Abstraction**: `ProductRepo` extends `JpaRepository` providing:

   - `save()`: Create and update operations
   - `findAll()`: Read operations with pagination
   - `deleteById()`: Delete operations

2. **Automatic Query Generation**: Spring Data JPA automatically generates queries based on method names

3. **Pagination Support**: Built-in pagination using `PageRequest` and `Page` interface

4. **Entity Mapping**: JPA annotations map Java objects to database tables

5. **Order Processing**: When placing an order:
   - Validates product existence
   - Checks stock availability
   - Updates product stock quantities
   - Creates order and order items
   - Returns order details with calculated totals

## 🛠️ Building the Project

To build the project without running it:

```bash
./mvnw clean package
```

This creates a JAR file in the `target/` directory that can be run with:

```bash
java -jar target/spring-data-jpa-0.0.1-SNAPSHOT.jar
```

## 🔐 Security Features

### Authentication Methods

1. **Username/Password Authentication**:

   - Traditional login with username and password
   - Secure password storage (Spring Security's BCryptPasswordEncoder)
   - JWT token-based session management

2. **OAuth2 Authentication (Google)**:
   - Sign in with Google account
   - Secure state validation to prevent CSRF attacks
   - Automatic user creation on first OAuth login

### Token Management

- **Access Tokens**: Short-lived tokens (default: 1 hour) for API access
- **Refresh Tokens**: Long-lived tokens (default: 7 days) stored securely in database
- **Token Refresh**: Secure refresh endpoint to obtain new access tokens
- **Token Validation**: Automatic JWT validation on protected endpoints

### Protected Endpoints

This application uses **annotation-based security** with Spring Security's `@PreAuthorize` annotations for fine-grained access control:

#### Security Annotations

- **`@PreAuthorize("isAuthenticated()")`** - Requires authentication (any logged-in user)
  - Applied at class level in `OrderController` - all order endpoints require authentication
- **`@PreAuthorize("hasRole('ADMIN')")`** - Requires authentication AND admin role
  - Applied to specific methods in `ProductController`:
    - `POST /api/product` - Create product
    - `PUT /api/product` - Update product
    - `DELETE /api/product/{id}` - Delete product

#### Authentication Required

Most endpoints require authentication via JWT token in the Authorization header:

```
Authorization: Bearer <your_access_token>
```

#### Public Endpoints (No Authentication)

The following endpoints are publicly accessible:

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/oauth2/authorize/{provider}` - OAuth2 initiation
- `GET /api/auth/oauth2/callback/{provider}` - OAuth2 callback
- `GET /api/products` - Browse products (paginated)
- `GET /api/products/search` - Search products
- `GET /api/product/{id}` - View product details
- `GET /api/product/{id}/image` - View product images

#### Protected Endpoints (Authentication Required)

- **All Order endpoints** - Class-level `@PreAuthorize("isAuthenticated()")` annotation
  - `POST /api/orders/place` - Place order
  - `GET /api/orders` - Get all orders (paginated)

#### Admin-Only Endpoints

The following endpoints require admin role (`ROLE_ADMIN`):

- `POST /api/product` - Create product (`@PreAuthorize("hasRole('ADMIN')")`)
- `PUT /api/product` - Update product (`@PreAuthorize("hasRole('ADMIN')")`)
- `DELETE /api/product/{id}` - Delete product (`@PreAuthorize("hasRole('ADMIN')")`)

**Note**: Role-based access control is configured in `SecurityConfig` with `@EnableMethodSecurity(prePostEnabled = true)` to enable method-level security annotations.

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [JWT.io](https://jwt.io/) - JWT token decoder and information
- [Google OAuth2 Documentation](https://developers.google.com/identity/protocols/oauth2)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Swagger/OpenAPI Documentation](https://swagger.io/docs/)

## 🐛 Troubleshooting

### Database Connection Issues

- Ensure PostgreSQL is running: `pg_isready` or check service status
- Verify database credentials in your `.env` file
- Check if the database `test` exists
- Verify PostgreSQL is listening on port 5432
- Ensure `DB_URL`, `DB_USER`, and `DB_PASSWORD` are correctly set

### Environment Variables Not Loading

- Make sure `.env` file exists in the project root directory
- Verify the file is named exactly `.env` (not `.env.txt` or similar)
- Check that `SPRING_PROFILES_ACTIVE` is set to `dev` or not set at all (for local `.env` loading)
- In production/staging, ensure environment variables are set in your deployment platform (Docker, Kubernetes, etc.)

### Google OAuth2 Issues

- Verify `GOOGLE_CLIENT_ID` and `GOOGLE_CLIENT_SECRET` are correctly set in `.env`
- Check that the redirect URI in Google Cloud Console matches: `http://localhost:8080/api/auth/oauth2/callback/google`
- Ensure Google+ API is enabled in your Google Cloud project
- Check that OAuth consent screen is configured in Google Cloud Console

### JWT Authentication Issues

- Verify `JWT_SECRET` is set and is at least 32 characters long (256 bits)
- Check that tokens are being sent in the `Authorization: Bearer <token>` header format
- Ensure `JWT_EXPIRATION` and `JWT_REFRESH_EXPIRATION` are set correctly (in milliseconds)
- If tokens expire quickly, increase the expiration values in `.env`

### Port Already in Use

If port 8080 is already in use, you can change it by adding to your `.env` file or setting an environment variable:

```bash
SERVER_PORT=8081
```

Or set it in `application.yml`:

```yaml
server:
  port: 8081
```

### Maven Build Issues

- Ensure Java 21 is installed: `java -version`
- Clear Maven cache: `./mvnw clean`
- Delete `target/` directory and rebuild
- Verify all required dependencies are in `pom.xml`

### Missing Dependencies

If you see errors about missing classes (e.g., `Dotenv`), ensure the `java-dotenv` dependency is included in `pom.xml`. The application uses this library for `.env` file loading in development mode.

## 📄 License

This project is for educational purposes.

---

**Happy Coding! 🎉**

For the best experience, use **Swagger UI** at: **http://localhost:8080/swagger-ui/index.html**
