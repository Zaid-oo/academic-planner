# Academic Planner REST API

Academic Planner is a Spring Boot REST API that I built to practice backend development using Java and Spring Boot.

The idea of the project is to help students manage their academic information such as courses, lectures, academic events, enrollments, and schedules.

I also used this project to practice important backend concepts like REST APIs, JPA relationships, authentication, authorization, testing, API documentation, environment variables, Git, and GitHub.

## Technologies Used

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Spring Security
- JWT
- BCrypt
- Swagger / OpenAPI
- JUnit
- MockMvc
- Maven
- Git
- GitHub

## Main Features

### Student Management

- Register a new student
- Login using university ID and password
- Get student information
- Edit student information
- Delete a student account

### Course Management

- Add courses
- Edit courses
- Delete courses
- Get courses
- Enroll a student in a course
- Remove a student from a course

### Lecture Management

- Add lectures
- Edit lectures
- Delete lectures
- Get lectures related to a course

### Academic Events

- Add academic events
- Edit academic events
- Delete academic events
- Get events
- Filter events by date
- Filter events by type
- Filter events by related course
- Filter important and unimportant events
- Get upcoming events
- Get past events
- Enroll a student in an event
- Remove a student from an event

### Student Schedule

Students can get their lecture schedule based on the courses they are enrolled in.

## Database Design

The project uses PostgreSQL.

The database name is:

```text
academic_planner
```

Main entities:

- Student
- Course
- Lecture
- AcademicEvent

Main relationships:

- Student and Course: Many-to-Many
- Student and AcademicEvent: Many-to-Many
- Course and Lecture: One-to-Many
- Course and AcademicEvent: One-to-Many

JPA and Hibernate are used to manage the entities, relationships, and database operations.

## Security

The project uses Spring Security with JWT-based authentication.

Student passwords are hashed using BCrypt before being stored in the database.

After a successful login, the server generates a JWT containing the student's university ID.

Protected requests must include the JWT in the Authorization header:

```text
Authorization: Bearer <JWT_TOKEN>
```

The application also has student-specific authorization.

A logged-in student cannot access another student's protected information by changing the university ID in the request URL.

The application uses stateless authentication, which means the server does not keep a login session for the user.

### Authentication Flow

```text
Student sends university ID and password
                ↓
Server checks the password using BCrypt
                ↓
Server generates a JWT
                ↓
Client sends the JWT with protected requests
                ↓
JwtAuthenticationFilter validates the token
                ↓
Spring Security authenticates the request
                ↓
The request continues to the controller
```

## Swagger / OpenAPI

The project uses Swagger / OpenAPI for API documentation and manual testing.

After starting the application, Swagger UI can be opened at:

```text
http://localhost:8080/swagger-ui.html
```

The generated OpenAPI documentation is available at:

```text
http://localhost:8080/v3/api-docs
```

Swagger also supports JWT authentication.

To test protected endpoints:

1. Login using `POST /student/login`.
2. Copy the returned JWT.
3. Click the **Authorize** button in Swagger.
4. Paste the token.
5. Test protected endpoints directly from Swagger.

## Automated Testing

The project includes automated tests using:

- JUnit
- Spring Boot Test
- MockMvc

The current test suite checks the following cases:

- Successful login returns a JWT
- Login with a wrong password returns `401 Unauthorized`
- A protected endpoint cannot be accessed without authentication
- A protected endpoint can be accessed using a valid JWT
- A student cannot access another student's protected information

Tests can be run using:

```bash
mvn test
```

## Environment Variables

Sensitive values are not stored directly in the source code.

The application uses the following environment variables:

```text
DB_PASSWORD
JWT_SECRET
```

They are referenced in `application.properties` like this:

```properties
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

This keeps the database password and JWT secret outside the Git repository.

## Project Structure

The project follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

- **Controller** handles HTTP requests and responses.
- **Service** contains the main business logic.
- **Repository** handles database operations using Spring Data JPA.
- **DTOs** are used to control the data sent to and received from the API.

A simplified project structure looks like this:

```text
src/
├── main/
│   ├── java/com/zaid/academicplanner/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── exception/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/zaid/academicplanner/
```

## How to Run the Project

1. Install Java 21.
2. Install PostgreSQL.
3. Create a PostgreSQL database called:

```text
academic_planner
```

4. Set the required environment variables:

```text
DB_PASSWORD
JWT_SECRET
```

5. Run the Spring Boot application.
6. Open Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

7. Register or login as a student.
8. Use the returned JWT to access protected endpoints.

## Example Login Request

```http
POST /student/login
Content-Type: application/json
```

Example request body:

```json
{
  "universityId": "202355555",
  "password": "yourPassword"
}
```

A successful login returns a JWT and the student response.

## Example Protected Request

Protected endpoints require:

```text
Authorization: Bearer <JWT_TOKEN>
```

For example:

```http
GET /student/{universityId}/schedule
Authorization: Bearer <JWT_TOKEN>
```

## Git and GitHub

Git is used for version control, and the project is stored on GitHub.

The basic workflow used for project changes is:

```bash
git status
git add .
git commit -m "commit message"
git push
```

## Learning Goals

This project helped me practice:

- Building a Spring Boot REST API
- Using a layered architecture
- Working with PostgreSQL
- Using JPA and Hibernate
- Designing entity relationships
- Using DTOs
- Applying validation and exception handling
- Implementing authentication and authorization
- Using BCrypt for password hashing
- Using JWT for stateless authentication
- Documenting APIs with Swagger
- Writing automated tests
- Moving secrets to environment variables
- Using Git and GitHub for version control

## Notes

This project was built as a learning and portfolio project.

My main goal was to practice building a complete backend application using Java and Spring Boot, starting from database design and REST endpoints, then adding security, testing, API documentation, environment variables, Git, and GitHub.

## Author

Zaid
