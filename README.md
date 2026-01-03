# Spring Data JPA - Product Management API

A RESTful API application built with Spring Boot and Spring Data JPA for managing products. This project demonstrates CRUD (Create, Read, Update, Delete) operations with pagination support using PostgreSQL as the database.

## 🚀 Features

- **CRUD Operations**: Full Create, Read, Update, Delete functionality for products
- **Pagination**: Get all products with pagination support
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
spring-data-jpa/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/mindtocode/springdatajpa/
│   │   │       ├── SpringDataJpaApplication.java    # Main application class
│   │   │       ├── controller/
│   │   │       │   └── ProductController.java       # REST controller (API endpoints)
│   │   │       ├── model/
│   │   │       │   └── Product.java                 # Entity class
│   │   │       ├── repo/
│   │   │       │   └── ProductRepo.java             # JPA repository interface
│   │   │       └── service/
│   │   │           └── ProductService.java          # Business logic layer
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

## 📝 API Endpoints Summary

| Method | Endpoint               | Description                  | Parameters                                      |
| ------ | ---------------------- | ---------------------------- | ----------------------------------------------- |
| POST   | `/product`             | Create a new product         | Request Body: Product JSON                      |
| GET    | `/product`             | Get all products (paginated) | Query: `page` (default: 0), `size` (default: 2) |
| PUT    | `/product`             | Update an existing product   | Request Body: Product JSON with id              |
| DELETE | `/product/{productId}` | Delete a product by ID       | Path: `productId`                               |

## 🏛️ Architecture Overview

This application follows a **layered architecture** pattern:

### 1. **Controller Layer** (`ProductController`)

- Handles HTTP requests and responses
- Maps URLs to service methods
- Uses `@RestController` for REST API endpoints
- Located in: `controller/ProductController.java`

### 2. **Service Layer** (`ProductService`)

- Contains business logic
- Orchestrates data operations
- Uses `@Service` annotation
- Located in: `service/ProductService.java`

### 3. **Repository Layer** (`ProductRepo`)

- Extends `JpaRepository` for database operations
- Provides CRUD methods automatically
- Uses `@Repository` annotation
- Located in: `repo/ProductRepo.java`

### 4. **Model/Entity Layer** (`Product`)

- Represents database table structure
- Uses JPA annotations (`@Entity`, `@Id`, etc.)
- Uses Lombok annotations for getters/setters
- Located in: `model/Product.java`

## 📊 Data Model

### Product Entity

```java
- id: Integer (Auto-generated primary key)
- name: String (Product name)
- specs: List<String> (Product specifications)
```

## ⚙️ Configuration

### Application Properties

The `application.properties` file contains:

```properties
spring.application.name=spring-data-jpa
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.username=postgres
spring.datasource.password=1212
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Key Settings**:

- `spring.jpa.hibernate.ddl-auto=update`: Automatically creates/updates database schema
- `spring.jpa.show-sql=true`: Shows SQL queries in console (useful for debugging)

## 🔍 How It Works

1. **Application Startup**: Spring Boot initializes the application context
2. **Database Connection**: Connects to PostgreSQL using configured credentials
3. **Schema Creation**: Hibernate automatically creates/updates the `product` table
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
