# Moon Dev Server

This is the server-side application for Moon, designed to work with the client-side React application and the admin panel React application.

## Table of Contents

- [Introduction](#introduction)
- [Technologies](#technologies)
- [Setup](#setup)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Moon Dev Server is a Spring Boot application that provides RESTful APIs for managing user responses, admin functionalities, and event logging. It is designed to work seamlessly with the Moon client and admin panel built with React.

## Technologies

- Java 21
- Spring Boot 3.3.4
- Maven
- MySQL
- Lombok
- Mustache
- Thymeleaf

## Setup

### Prerequisites

- Java 21
- Maven
- MySQL

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/moon-dev-server.git
    cd moon-dev-server
    ```

2. Configure the database in `src/main/resources/application.properties`:
    ```ini
    spring.datasource.url=jdbc:mysql://localhost:3306/moondev_db?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=root
    ```

3. Build the project:
    ```bash
    mvn clean install
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Usage

The server will start on `http://localhost:8080`. You can access the APIs using any HTTP client like Postman or through the client and admin panel applications.

## API Endpoints

### User Responses

- `POST /response/user` - Save a new user response
- `GET /response/users` - Retrieve all user responses
- `GET /response/user/{id}` - Retrieve a user response by ID
- `PUT /response/user/{id}` - Update a user response by ID
- `DELETE /response/user/{id}` - Delete a user response by ID

### Admin

- `POST /adminpage/admin` - Save a new admin
- `GET /adminpage/admins` - Retrieve all admins
- `GET /adminpage/admin/{id}` - Retrieve an admin by ID
- `PUT /adminpage/admin/{id}` - Update an admin by ID
- `DELETE /adminpage/admin/{id}` - Delete an admin by ID

### Roles

- `POST /adminpage/role` - Save a new role
- `GET /adminpage/roles` - Retrieve all roles
- `PUT /adminpage/role/{id}` - Update a role by ID
- `DELETE /adminpage/role/{id}` - Delete a role by ID

### Event Logs

- `GET /eventlog/all` - Retrieve all event logs

### Authentication

- `POST /auth/login` - Login and create a session

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the Eclipse Public License 2.0.
