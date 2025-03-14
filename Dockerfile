FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo gradle y el código fuente
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY src/ src/

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh


# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando por defecto para ejecutar la aplicación
CMD ["./gradlew", "bootRun"]
