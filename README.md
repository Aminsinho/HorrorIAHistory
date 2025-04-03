**How to Start**

To start the application using Docker and Ollama Llama 3.2, follow these steps:

1. **Install Ollama Llama 3.2**: Ensure that Ollama Llama 3.2 is installed on your computer. You can download it from the official Ollama website.
2. **Build the Docker images**: Run `docker-compose build` to build the Docker images.
3. **Start the application**: Use Docker Compose to start the application and its dependencies by running `docker-compose up`.
4. **Access the application**: The application will be available at `http://localhost:8080`.

**Cómo empezar**

Para iniciar la aplicación utilizando Docker y Ollama Llama 3.2, sigue estos pasos:

1. **Instalar Ollama Llama 3.2**: Asegúrate de que Ollama Llama 3.2 esté instalado en tu ordenador. Puedes descargarlo desde el sitio web oficial de Ollama.
2. **Construir las imágenes de Docker**: Ejecuta `docker-compose build` para construir las imágenes de Docker.
3. **Iniciar la aplicación**: Usa Docker Compose para iniciar la aplicación y sus dependencias ejecutando `docker-compose up`.
4. **Acceder a la aplicación**: La aplicación estará disponible en `http://localhost:8080`.

### Project Description

This project is a psychological horror game where players must make decisions to escape from a hypothetical location. The game challenges players with moral dilemmas and unpredictable scenarios, creating an immersive and suspenseful experience.

### Descripción del proyecto

Este proyecto es un juego de terror psicológico en el que los jugadores deben tomar decisiones para escapar de un lugar hipotético. El juego desafía a los jugadores con dilemas morales y escenarios impredecibles, creando una experiencia inmersiva y llena de suspenso.

### Project Features

This project is a Spring Boot application that uses Gradle for build automation. It includes the following components:

- **Java**: The primary programming language used in this project.
- **Spring Boot**: A framework for building Java-based web applications.
- **Gradle**: A build automation tool used to compile and package the application.
- **PostgreSQL**: A relational database used to store application data.
- **Docker**: Used to containerize the application and its dependencies.
- **Docker Compose**: A tool for defining and running multi-container Docker applications.
- **Ollama Llama 3.2**: A local AI model used for advanced functionalities, such as generating dynamic game scenarios and dialogues.

### Características del proyecto

Este proyecto es una aplicación de Spring Boot que utiliza Gradle para la automatización de compilación. Incluye los siguientes componentes:

- **Java**: El lenguaje de programación principal utilizado en este proyecto.
- **Spring Boot**: Un framework para construir aplicaciones web basadas en Java.
- **Gradle**: Una herramienta de automatización de compilación utilizada para compilar y empaquetar la aplicación.
- **PostgreSQL**: Una base de datos relacional utilizada para almacenar los datos de la aplicación.
- **Docker**: Utilizado para contenerizar la aplicación y sus dependencias.
- **Docker Compose**: Una herramienta para definir y ejecutar aplicaciones de múltiples contenedores Docker.
- **Ollama Llama 3.2**: Un modelo de IA local utilizado para funcionalidades avanzadas, como generar escenarios dinámicos y diálogos en el juego.

### Changing Database Creation Mode

To change the database creation mode, modify the `application.properties` file in the `src/main/resources` directory. For example, to set the mode to `update`, add the following line:

```properties
spring.jpa.hibernate.ddl-auto=update
```

### Cambiar el modo de creación de la base de datos

Para cambiar el modo de creación de la base de datos, modifica el archivo `application.properties` en el directorio `src/main/resources`. Por ejemplo, para establecer el modo en `update`, añade la siguiente línea:

```properties
spring.jpa.hibernate.ddl-auto=update
```

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

### Estructura del proyecto

La estructura del proyecto es la siguiente:

- `src/main/java`: Contiene el código fuente en Java de la aplicación.
- `src/main/resources`: Contiene las propiedades y archivos de configuración de la aplicación.
- `src/test/java`: Contiene el código fuente de las pruebas de la aplicación.
- `src/test/resources`: Contiene las propiedades y archivos de configuración de las pruebas.
- `build.gradle`: Contiene el script de compilación de Gradle para la aplicación.
- `Dockerfile`: Contiene el Dockerfile para construir la imagen de la aplicación.
- `docker-compose.yml`: Contiene la configuración de Docker Compose para ejecutar la aplicación y sus dependencias.
- `README.md`: Contiene información sobre el proyecto y cómo iniciarlo.

### Running Tests

To run the tests for the application, use the following command:

```bash
./gradlew test
```

### Ejecutar pruebas

Para ejecutar las pruebas de la aplicación, utiliza el siguiente comando:

```bash
./gradlew test
```

### Running the Application

To run the application using Docker Compose, follow these steps:

1. **Install Ollama Llama 3.2**: Ensure that Ollama Llama 3.2 is installed and running on your computer.
2. **Build the Docker images**: Run `docker-compose build` to build the Docker images.
3. **Start the application**: Use Docker Compose to start the application and its dependencies by running `docker-compose up`.
4. **Access the application**: The application will be available at `http://localhost:8080`.

### Ejecutar la aplicación

Para ejecutar la aplicación utilizando Docker Compose, sigue estos pasos:

1. **Instalar Ollama Llama 3.2**: Asegúrate de que Ollama Llama 3.2 esté instalado y funcionando en tu ordenador.
2. **Construir las imágenes de Docker**: Ejecuta `docker-compose build` para construir las imágenes de Docker.
3. **Iniciar la aplicación**: Usa Docker Compose para iniciar la aplicación y sus dependencias ejecutando `docker-compose up`.
4. **Acceder a la aplicación**: La aplicación estará disponible en `http://localhost:8080`.

### Accessing the Database

To access the PostgreSQL database, use the following credentials:

- **Host**: `localhost`
- **Port**: `5432`
- **Database Name**: `mydatabase`
- **Username**: `user`
- **Password**: `admin`

You can use a database client such as `pgAdmin` or `DBeaver` to connect to the database using these credentials.

### Acceder a la base de datos

Para acceder a la base de datos PostgreSQL, utiliza las siguientes credenciales:

- **Host**: `localhost`
- **Puerto**: `5432`
- **Nombre de la base de datos**: `mydatabase`
- **Usuario**: `user`
- **Contraseña**: `admin`

Puedes usar un cliente de base de datos como `pgAdmin` o `DBeaver` para conectarte a la base de datos utilizando estas credenciales.

### To create a user, use the following endpoint:

- **URL**: `http://localhost:8080/users/register`
- **Method**: `POST`
- **Request Body Example**:

````json
{"username": "testuser", "password": "testpassword"}
````

### Para crear un usuario, utiliza el siguiente endpoint:

- **URL**: `http://localhost:8080/users/register`
- **Método**: `POST`
- **Ejemplo de cuerpo de solicitud**:

````json
{"username": "testuser", "password": "testpassword"}
````

### To authenticate a user, use the following endpoint:

- **URL**: `http://localhost:8080/token`
- **Method**: `POST`
- **Request Body Example**:

````json
{"username": "testuser", "password": "testpassword"}
````

### Para autenticar un usuario, utiliza el siguiente endpoint:

- **URL**: `http://localhost:8080/token`
- **Método**: `POST`
- **Ejemplo de cuerpo de solicitud**:

````json
{"username": "testuser", "password": "testpassword"}
````

### To access a protected endpoint, use the following endpoint:

- **URL**: `http://localhost:8080/hello`
- **Method**: `GET`
- **Headers**: `Authorization: Bearer <token>`

### Para acceder a un endpoint protegido, utiliza el siguiente endpoint:

- **URL**: `http://localhost:8080/hello`
- **Método**: `GET`
- **Encabezados**: `Authorization: Bearer <token>`

