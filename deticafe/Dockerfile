FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the application files
COPY . .

EXPOSE 8081

# Set the entry point
ENTRYPOINT ["./mvnw", "spring-boot:run"]