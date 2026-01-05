# ecommerce-backend

A RESTful API application built with Spring Boot and Spring Data JPA for managing products and orders. This ecommerce backend demonstrates CRUD (Create, Read, Update, Delete) operations with pagination support using PostgreSQL as the database.

## 🎨 Frontend Application

This backend API is designed to work with the **ecommerce-frontend** React application:

**Frontend Repository**: [https://github.com/mhmdfathy96/ecommerce-frontend](https://github.com/mhmdfathy96/ecommerce-frontend)

The frontend is built with React and connects to this backend API to provide a complete ecommerce experience. The backend API includes CORS configuration to allow requests from the React frontend running on `http://localhost:5173`.

## 🚀 Features

- **CRUD Operations**: Full Create, Read, Update, Delete functionality for products
- **Order Management**: Place orders and retrieve order history with pagination
- **Stock Management**: Automatic stock validation and updates when placing orders
- **Pagination**: Get all products and orders with pagination support
- **Global Exception Handling**: Meaningful error responses (400 Bad Request) instead of generic 500 errors
- **Spring Data JPA**: Simplified data access using JPA repositories
- **PostgreSQL**: Robust relational database for data persistence
- **Swagger UI**: Interactive API documentation and testing interface
- **Lombok**: Reduces boilerplate code with annotations

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21** (or higher)
- **Maven 3.6+** (or use the included Maven Wrapper)
- **PostgreSQL** (version 12 or higher)
- **Postman** (optional, for API testing) or use Swagger UI

## 🗄️ Database Setup

### 1. Install PostgreSQL

If you don't have PostgreSQL installed, download and install it from [PostgreSQL Official Website](https://www.postgresql.org/download/).

### 2. Create Database

1. Open PostgreSQL command line (psql) or use a GUI tool like pgAdmin
2. Connect to PostgreSQL server (default user is usually `postgres`)
3. Create the database:

```sql
CREATE DATABASE test;
```

### 3. Update Database Credentials

Update the database credentials in `src/main/resources/application.properties` if needed:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.username=postgres
spring.datasource.password=your_password
```

**Note**: The current configuration uses:

- Database name: `test`
- Username: `postgres`
- Password: `1212`

Change the password to match your PostgreSQL setup.

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

### Product Endpoints

| Method | Endpoint                  | Description                  | Parameters                                      |
| ------ | ------------------------- | ---------------------------- | ----------------------------------------------- |
| POST   | `/api/product`            | Create a new product         | Request Body: Product JSON                      |
| GET    | `/api/products`           | Get all products (paginated) | Query: `page` (default: 0), `size` (default: 5) |
| GET    | `/api/products/search`    | Search products by keyword   | Query: `page`, `size`, `keyword`                |
| GET    | `/api/product/{id}`       | Get product by ID            | Path: `productId`                               |
| PUT    | `/api/product`            | Update an existing product   | Request Body: Product JSON with id              |
| DELETE | `/api/product/{id}`       | Delete a product by ID       | Path: `productId`                               |
| GET    | `/api/product/{id}/image` | Get product image            | Path: `productId`                               |

### Order Endpoints

| Method | Endpoint            | Description                | Parameters                                      |
| ------ | ------------------- | -------------------------- | ----------------------------------------------- |
| POST   | `/api/orders/place` | Place a new order          | Request Body: OrderRequest JSON                 |
| GET    | `/api/orders`       | Get all orders (paginated) | Query: `page` (default: 0), `size` (default: 5) |

## 🏛️ Architecture Overview

This application follows a **layered architecture** pattern:

### 1. **Controller Layer**

- **`ProductController`**: Handles product-related HTTP requests and responses
- **`OrderController`**: Handles order-related HTTP requests and responses
- Maps URLs to service methods
- Uses `@RestController` for REST API endpoints
- Located in: `controller/` package

### 2. **Service Layer**

- **`ProductService`**: Contains product business logic
- **`OrderService`**: Contains order business logic, handles stock validation and updates
- Orchestrates data operations
- Uses `@Service` annotation
- Located in: `service/` package

### 3. **Repository Layer**

- **`ProductRepo`**: Extends `JpaRepository` for product database operations
- **`OrderRepo`**: Extends `JpaRepository` for order database operations
- Provides CRUD methods automatically
- Located in: `repo/` package

### 4. **Model/Entity Layer**

- **`Product`**: Represents product database table structure
- **`Order`**: Represents order database table structure
- **`OrderItem`**: Represents order items (many-to-one relationship with Order and Product)
- Uses JPA annotations (`@Entity`, `@Id`, `@OneToMany`, `@ManyToOne`, etc.)
- Uses Lombok annotations for getters/setters
- Located in: `model/` package

### 5. **DTO Layer**

- **`OrderRequest`**: Request DTO for placing orders
- **`OrderResponse`**: Response DTO for order data
- **`OrderItemRequest`**: Request DTO for order items
- **`OrderItemResponse`**: Response DTO for order item data
- Located in: `model/dto/` package

### 6. **Exception Handling**

- **`GlobalExceptionHandler`**: Global exception handler using `@RestControllerAdvice`
- Handles custom exceptions (`ProductNotFoundException`, `ProductOutOfStockException`)
- Returns meaningful 400 Bad Request responses instead of 500 errors
- Located in: `exceptions/` package

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

### Relationships

- **Order** ↔ **OrderItem**: One-to-Many (One order can have multiple order items)
- **Product** ↔ **OrderItem**: One-to-Many (One product can be in multiple order items)
- **OrderItem** → **Order**: Many-to-One (Each order item belongs to one order)
- **OrderItem** → **Product**: Many-to-One (Each order item references one product)

## ⚙️ Configuration

### Application Properties

The `application.properties` file contains:

```properties
spring.application.name=ecommerce-backend
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.username=postgres
spring.datasource.password=1212
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Key Settings**:

- `spring.jpa.hibernate.ddl-auto=update`: Automatically creates/updates database schema
- `spring.jpa.show-sql=true`: Shows SQL queries in console (useful for debugging)

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

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Swagger/OpenAPI Documentation](https://swagger.io/docs/)

## 🐛 Troubleshooting

### Database Connection Issues

- Ensure PostgreSQL is running: `pg_isready` or check service status
- Verify database credentials in `application.properties`
- Check if the database `test` exists
- Verify PostgreSQL is listening on port 5432

### Port Already in Use

If port 8080 is already in use, change it in `application.properties`:

```properties
server.port=8081
```

### Maven Build Issues

- Ensure Java 21 is installed: `java -version`
- Clear Maven cache: `./mvnw clean`
- Delete `target/` directory and rebuild

## 📄 License

This project is for educational purposes.

---

**Happy Coding! 🎉**

For the best experience, use **Swagger UI** at: **http://localhost:8080/swagger-ui/index.html**
