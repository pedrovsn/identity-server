# Identity Server using Kotlin and Spring Security

Welcome to the Identity Server project built using Kotlin and Spring Security! This server provides secure and efficient authentication and authorization services, helping you manage user identities and access to resources.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Configuration](#configuration)
- [Usage](#usage)
    - [User Registration](#user-registration)
    - [User Login](#user-login)
    - [Access Control](#access-control)
- [API Documentation](#api-documentation)
- [Security](#security)
- [License](#license)

## Introduction

The Identity Server is a secure authentication and authorization solution built using the Kotlin programming language and the Spring Security framework. It helps you secure your applications by providing robust user identity management, role-based access control, and token-based authentication.

## Features

- User registration and management.
- Secure token-based authentication.
- Role-based access control.
- JWT (JSON Web Tokens) for token generation and verification.
- RESTful API endpoints for user authentication and management.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher installed.
- Maven build tool.

### Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/pedrovsn/identity-server.git
   cd identity-server
   ```

2. Build the project using Gradle:

   ```bash
   mvn clean install
   ```

### Configuration

1. Configure JWT settings in `src/main/resources/application.yaml`.

## Usage

### User Registration

To register a new user, send a POST request to `/api/register` with a JSON body containing the user's registration details.

Example:

```http
POST /api/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "secretpassword",
  "email": "newuser@example.com"
}
```

### User Login

To authenticate a user and obtain a JWT token, send a POST request to `/api/login` with the user's credentials.

Example:

```http
POST /api/login
Content-Type: application/json

{
  "username": "newuser",
  "password": "secretpassword"
}
```

### Access Control

Secure your resources by using Spring Security annotations on your endpoints, such as `@PreAuthorize` for role-based access control.

Example:

```kotlin
import org.springframework.security.access.prepost.PreAuthorize

@RestController
class ResourceController {

    @GetMapping("/secure/resource")
    @PreAuthorize("hasRole('ROLE_USER')")
    fun secureResource(): String {
        return "This is a secure resource."
    }
}
```

## API Documentation

API documentation is generated using Swagger. Access the documentation at `http://localhost:8080/swagger-ui.html` after starting the server.

## Security

- Passwords are securely hashed using strong encryption algorithms.
- JWT tokens are signed to prevent tampering.
- Role-based access control ensures proper authorization.

## License

This project is licensed under the [MIT License](LICENSE).

---