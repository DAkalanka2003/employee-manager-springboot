# Employee Management System

A Spring Boot CRUD Web Application built with MySQL, Spring Data JPA, Lombok, Thymeleaf, and Bean Validation.

## Features
- **Create Employee**: Add employee records with validation (Name, Email, 10-digit Phone, Department, Salary).
- **Read & View**: Interactive employee list with live employee count.
- **Update Employee**: Edit existing employee details with pre-populated form and duplicate checks.
- **Delete Employee**: Remove employees with interactive confirmation.
- **Search (Task 11 Challenge)**: Search employees by full name (case-insensitive substring match).
- **REST API Support (Task 9)**: Full REST endpoints (`/api/employees`) supporting JSON CRUD requests for Postman.

## Prerequisites
- JDK 17 or JDK 21+ (Adoptium OpenJDK recommended)
- MySQL Server 8.x / XAMPP MariaDB (running on default port `3306`)
- Maven (or included `./mvnw` wrapper)

## Database Configuration
1. Open MySQL terminal or phpMyAdmin and execute:
   ```sql
   CREATE DATABASE employee_db;
   ```
2. Application database settings are configured in `src/main/resources/application.properties`:
   ```properties
   spring.application.name=employee-manager
   spring.datasource.url=jdbc:mysql://localhost:3306/employee_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.thymeleaf.cache=false
   ```
   *(Optional) You can copy `secrets.properties.example` to `secrets.properties` for externalized passwords.*

## How to Build and Run

### 1. Build the application:
```powershell
.\mvnw.cmd clean package
```

### 2. Run the application:
```powershell
.\mvnw.cmd spring-boot:run
```
Or execute the packaged JAR:
```powershell
java -jar target/employee-manager-0.0.1-SNAPSHOT.jar
```

### 3. Open in Browser:
- Web Application: [http://localhost:8080/](http://localhost:8080/) or [http://localhost:8080/employees](http://localhost:8080/employees)

## Controller Endpoints (MVC & REST)

### MVC Web Endpoints
| HTTP Method | URL | Purpose |
|---|---|---|
| `GET` | `/` | Redirects to `/employees` |
| `GET` | `/employees` | Displays form and list (supports `?keyword=...` search) |
| `POST` | `/employees` | Saves new employee with validation |
| `GET` | `/employees/{id}/edit` | Shows edit form with employee data |
| `POST` | `/employees/{id}/update` | Updates employee details |
| `POST` | `/employees/{id}/delete` | Deletes employee record |

### REST API Endpoints (Postman)
| HTTP Method | URL | Purpose |
|---|---|---|
| `GET` | `/api/employees` | List all employees (JSON) / filter by `?keyword=...` |
| `GET` | `/api/employees/{id}` | Get employee by ID (JSON) |
| `POST` | `/api/employees` | Create employee (JSON body) |
| `PUT` | `/api/employees/{id}` | Update employee (JSON body) |
| `DELETE` | `/api/employees/{id}` | Delete employee by ID |

## Postman Testing
Import the included `postman_collection.json` directly into Postman to run all pre-configured CRUD requests.
