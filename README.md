**How to Start**

To start the application using Docker, follow these steps:

1. **Build the Docker images**: Run `docker-compose build` to build the Docker images.
2. **Start the application**: Use Docker Compose to start the application and its dependencies by running `docker-compose up`.
3. **Access the application**: The application will be available at `http://localhost:8080`.


### Project Features

This project is a Spring Boot application that uses Gradle for build automation. It includes the following components:

- **Java**: The primary programming language used in this project.
- **Spring Boot**: A framework for building Java-based web applications.
- **Gradle**: A build automation tool used to compile and package the application.
- **PostgreSQL**: A relational database used to store application data.
- **Docker**: Used to containerize the application and its dependencies.
- **Docker Compose**: A tool for defining and running multi-container Docker applications.

### Changing Database Creation Mode

To change the database creation mode, modify the `application.properties` file in the `src/main/resources` directory. For example, to set the mode to `update`, add the following line:

```properties
spring.jpa.hibernate.ddl-auto=update