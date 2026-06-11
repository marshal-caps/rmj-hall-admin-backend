# Stage 1: Build the application using Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application and skip tests to speed up deployment
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the compiled jar file from the build stage
COPY --from=build /app/target/RMJHallAdmin-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the application with optimized memory settings for Render's free tier
ENTRYPOINT ["java", "-Xmx300m", "-jar", "app.jar"]