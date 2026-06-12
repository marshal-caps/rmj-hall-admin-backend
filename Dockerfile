# Stage 1: Build the application using Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application and completely bypass DB check at build time
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the compiled jar file from the build stage
COPY --from=build /app/target/RMJHallAdmin-0.0.1-SNAPSHOT.jar app.jar

# Expose the standard port
EXPOSE 8080

# Cleaned entrypoint so Railway can inject environment variables natively
ENTRYPOINT ["java", "-jar", "app.jar"]