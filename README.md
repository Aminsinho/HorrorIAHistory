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
````

### Project Structure

The project structure is as follows:

- `src/main/java`: Contains the Java source code for the application.
- `src/main/resources`: Contains the application properties and configuration files.
- `src/test/java`: Contains the test source code for the application.
- `src/test/resources`: Contains the test properties and configuration files.
- `build.gradle`: Contains the Gradle build script for the application.
- `Dockerfile`: Contains the Dockerfile for building the application image.
- `docker-compose.yml`: Contains the Docker Compose configuration for running the application and its dependencies.
- `README.md`: Contains information about the project and how to start the application.



### Running Tests

To run the tests for the application, use the following command:

```bash 
./gradlew test
````

### Running the Application

To run the application using Docker Compose, follow these steps:

1. **Build the Docker images**: Run `docker-compose build` to build the Docker images.
2. **Start the application**: Use Docker Compose to start the application and its dependencies by running `docker-compose up`.
3. **Access the application**: The application will be available at `http://localhost:8080`.

### Accessing the Database

To access the PostgreSQL database, use the following credentials:


- **Host**: `localhost`
- **Port**: `5432`
- **Database Name**: `mydatabase`
- **Username **: `user`
- **Password**: `admin`

You can use a database client such as `pgAdmin` or `DBeaver` to connect to the database using these credentials.


### To create a user, use the following endpoint:

- **URL**: `http://localhost:8080/users/register` 
- **Method**: `POST`
- **Request Body Example**:

````json
'{"username": "testuser", "password": "testpassword"}'
````

### To authenticate a user, use the following endpoint:
- **URL**: `http://localhost:8080/token`
- **Method**: `POST`
- **Request Body Example**:

````json
'{"username": "testuser", "password": "testpassword"}'
````

### To access a protected endpoint, use the following endpoint:

- **URL**: `http://localhost:8080/hello`
- **Method**: `GET`
- **Headers**: `Authorization: Bearer <token>`

