# Simple CRUD API with Authentication

## Overview
This project is a simple CRUD API built using the Spring Boot framework as part of internship at Innowise Group. It allows users to create accounts, log in, view a list of users, and delete users by their ID. The API also includes authentication using JWT (JSON Web Tokens) to ensure that viewing, updating and deleting users can only be performed by authenticated users. PostgreSQL is used as the database, and Docker is utilized to run the PostgreSQL container.

---

## Features
- **Endpoints:**
  - `POST /register` - Register a new user.
  - `POST /login` - Log in and receive a JWT token.
  - `GET /users` - Retrieve all registered users (requires authentication).
  - `PUT /users/{id}` - Update a user by ID (requires authentication).
  - `DELETE /users/{id}` - Delete a user by ID (requires authentication).
- **JWT Authentication**: Protects endpoints for authorized users only.
- **PostgreSQL Database**: Stores user data securely.

---

## Setting Up and Running the Application
Follow these steps to set up and run the application:

### 1. Prerequisites
Make sure the following are installed on your system:
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11 or higher)
- [Apache Maven](https://maven.apache.org/install.html)
- [Git](https://git-scm.com/downloads)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)

### 2. Clone the Repository
Clone the project from GitHub:
```bash
git clone https://github.com/bereketab24/Innowise_Spring_Boot_Project.git
```

### 3. Navigate to the Project Directory
Move into the cloned project directory:
```bash
cd <project-folder>
```

### 4. Install Dependencies
Use Maven to install all project dependencies:
```bash
mvn clean install
```

### 5. Set Up and Run PostgreSQL in Docker
[Install Docker](https://www.docker.com/products/docker-desktop)
Run the following command to create and start a PostgreSQL container:
```bash
docker run --name postgres-db \
  -e POSTGRES_DB=CRUDAPI \
  -e POSTGRES_USER=bereketab24 \
  -e POSTGRES_PASSWORD=bereketab24 \
  -p 5432:5432 \
  -d postgres
```
- **Explanation of the command:**
  - `POSTGRES_DB=CRUDAPI`: The name of the database.
  - `POSTGRES_USER=bereketab24`: The username for the database.
  - `POSTGRES_PASSWORD=bereketab24`: The password for the database.
  - `-p 5432:5432`: Maps port 5432 of the container to port 5432 of your machine.

### 6. Start the Spring Boot Application
Run the application using Maven:
```bash
mvn spring-boot:run
```
- The app will start and listen on port `8080` by default.

---

## Testing the API Endpoints
You can test the API using tools like [Postman](https://www.postman.com/).

### Example Requests

#### 1. **Register a New User**
- **Endpoint**: `POST /register`
- **Body (JSON)**:
  ```json
  {
    "username": "testuser",
    "password": "password123"
  }
  ```

#### 2. **Log In**
- **Endpoint**: `POST /login`
- **Body (JSON)**:
  ```json
  {
    "username": "testuser",
    "password": "password123"
  }
  ```
- **Response**: A JWT token.

#### 3. **Get All Users** (Requires Authentication)
- **Endpoint**: `GET /users`
- **Headers**:
  ```json
  {
    "Authorization": "Bearer <JWT-TOKEN>"
  }
  ```

#### 4. **Delete a User** (Requires Authentication)
- **Endpoint**: `DELETE /users/{id}`
- **Headers**:
  ```json
  {
    "Authorization": "Bearer <JWT-TOKEN>"
  }
  ```

---

## Configuring the Application
The application is configured using the `application.properties` file. This file is located in the `src/main/resources` directory and contains the following configurations:

### PostgreSQL Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/CRUDAPI
spring.datasource.username=bereketab24
spring.datasource.password=bereketab24
spring.datasource.driver-class-name=org.postgresql.Driver
```

### JWT Configuration
```properties
jwt.secret=bereketab24-secret-key-innowise-internship
```

### JPA (Hibernate) Configuration
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
```

If you want to use different database credentials or JWT secrets, update the `application.properties` file accordingly.

---

## Additional Notes
- **Database Schema**: The schema and tables are automatically created based on the `User` entity when the application starts. No manual SQL scripts are needed.
- **Logs**: SQL queries and Hibernate logs can be viewed in the console for debugging.

---

## License
This project is for educational purposes as part of an internship program.

