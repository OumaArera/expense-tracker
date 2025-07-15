# Expense Tracker API

A Spring Boot-based RESTful API that allows a user to manage personal expenses and categories. It supports full CRUD operations and basic filtering, with PostgreSQL as the database. All endpoints have been thoroughly tested using unit tests, Swagger, and Postman.

---

## Project Overview

This API enables a user to:

- Create, read, update, and delete **categories**
- Create, read, update, and delete **expenses**
- Filter expenses by **description** and **date range**
- Associate each expense with a category
- Perform all actions without authentication (for demo purposes)

---

## Tech Stack

| Layer            | Technology               |
|------------------|--------------------------|
| Language         | Java 24                  |
| Framework        | Spring Boot (v3.5.3)     |
| ORM              | Spring Data JPA + Hibernate |
| Database         | PostgreSQL (hosted online) |
| Testing          | JUnit 5, Mockito         |
| Build Tool       | Maven                    |
| API Testing Tool | Swagger, Postman         |

---

## Features Implemented

### Category APIs

| Method | Endpoint                    | Description         |
|--------|-----------------------------|---------------------|
| GET    | `/api/categories`           | List all categories |
| POST   | `/api/categories`           | Create a category   |
| PUT    | `/api/categories/{id}`      | Update a category   |
| DELETE | `/api/categories/{id}`      | Delete a category   |

**Sample Payload**
```json
{
  "name": "Food"
}
```

---

### Expense APIs

| Method | Endpoint             | Description                    |
| ------ | -------------------- | ------------------------------ |
| GET    | `/api/expenses`      | List all expenses with filters |
| GET    | `/api/expenses/{id}` | Retrieve an expense            |
| POST   | `/api/expenses`      | Create a new expense           |
| PUT    | `/api/expenses/{id}` | Update an expense              |
| DELETE | `/api/expenses/{id}` | Delete an expense              |

**Filtering Parameters for `/api/expenses`:**

* `search`: (optional) filter by description
* `startDate`: default = `2025-01-01`
* `endDate`: default = `2100-01-01`
* `page`: default = 0
* `size`: default = 10

**Sample Payload**

```json
{
  "description": "Sukari",
  "amount": 450.0,
  "date": "2025-07-11",
  "categoryId": "97b4ca11-3840-4e49-895c-3eb1bdaf8dd3"
}
```

---

## Testing

* **Unit tests** written for services using **JUnit 5** and **Mockito**
* All tests pass successfully
* APIs verified using both **Swagger UI** and **Postman**

**Running Tests**

```bash
mvn test
```

Test output (excerpt):

```
Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## API Documentation

Once the application is running, access Swagger UI at:

**`http://localhost:8080/swagger-ui/index.html`**

---

## Configuration

### PostgreSQL Database

* The application connects to a PostgreSQL database hosted online.
* All credentials and sensitive configurations are stored securely in a `.env` file (not included in version control).



## Running the Application

```bash
mvn spring-boot:run
```

---

## Folder Structure

```
expense-tracker/
├── certs/                              # Certificate files
├── src/
│   ├── main/
│   │   ├── java/com/expensetracker/
│   │   │   ├── config/                 # Custom configurations
│   │   │   │   └── DotenvLoader.java
│   │   │   ├── controller/             # REST API controllers
│   │   │   │   ├── CategoryController.java
│   │   │   │   └── ExpenseController.java
│   │   │   ├── dto/                    # Data Transfer Objects
│   │   │   │   ├── CategoryDTO.java
│   │   │   │   └── ExpenseDTO.java
│   │   │   ├── model/                  # JPA entity classes
│   │   │   │   ├── Category.java
│   │   │   │   └── Expense.java
│   │   │   ├── repository/             # Spring Data JPA repositories
│   │   │   │   ├── CategoryRepository.java
│   │   │   │   └── ExpenseRepository.java
│   │   │   ├── service/                # Business logic
│   │   │   │   ├── impl/
│   │   │   │   │   ├── CategoryServiceImpl.java
│   │   │   │   │   └── ExpenseServiceImpl.java
│   │   │   │   ├── CategoryService.java
│   │   │   │   └── ExpenseService.java
│   │   │   └── ExpenseTrackerApplication.java  # Spring Boot main class
│   ├── resources/                      # Application configuration files
│   │   └── application.properties 
│
├── src/test/java/com/expensetracker/  # Unit test classes
│   ├── CategoryServiceTest.java
│   ├── ExpenseServiceTest.java
│   └── ExpenseTrackerApplicationTests.java
│
├── pom.xml                             # Maven build configuration
├── mvnw, mvnw.cmd                      # Maven wrapper scripts
├── README.md                           # Project documentation
└── HELP.md (optional)                  # Spring Boot generated help file

```

---


## Author

**John Ouma**
Email: `johnouma999@gmail.com`
GitHub: [`github.com/OumaArera`](https://github.com/OumaArera/expense-tracker)

---

