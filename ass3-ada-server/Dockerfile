# Use a base image with Java and Spring Boot support
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the application JAR file
COPY build/libs/ass3-ada-server-0.0.1-SNAPSHOT.jar app.jar

# Make directory for logs
RUN mkdir -p /app/logs

# Expose the server port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
