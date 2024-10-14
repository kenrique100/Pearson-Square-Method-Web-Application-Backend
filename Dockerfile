# Stage 1: Build the application using Maven and OpenJDK 17
FROM huangzp88/maven-openjdk17 AS build

# Set the working directory
WORKDIR /app

# Set Maven options to increase timeout and provide better debug output
ENV MAVEN_OPTS="-Dmaven.wagon.http.connectionTimeout=60000 -Dmaven.wagon.http.readTimeout=60000"

# Clean local Maven repository to ensure no corrupt or incomplete files
RUN rm -rf /root/.m2/repository/*

# Copy the POM file
COPY pom.xml .

# Purge local repository and download dependencies offline
RUN mvn dependency:purge-local-repository -B -X
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build and package the application
RUN mvn clean install -DskipTests

# Stage 2: Running the app
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/feedFormulation-0.0.1-SNAPSHOT.jar .

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/feedFormulation-0.0.1-SNAPSHOT.jar"]
