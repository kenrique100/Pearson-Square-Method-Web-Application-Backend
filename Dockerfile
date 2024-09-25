# Stage 1: Build the application
FROM maven:3.6.3-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the pom.xml and download dependencies (leverages Docker cache)
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src ./src

# Build the application and skip tests for faster build
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the previous build stage
COPY --from=build /app/target/feedFormulation-0.0.1-SNAPSHOT.jar .

# Expose the port the application runs on
EXPOSE 8080

# Run the application with the specified JAVA_OPTS
ENTRYPOINT ["java", "-jar", "/app/feedFormulation-0.0.1-SNAPSHOT.jar"]
