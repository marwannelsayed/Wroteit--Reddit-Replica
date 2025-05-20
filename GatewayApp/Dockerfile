# Use Official OpenJDK Image
FROM openjdk:25-ea-4-jdk-oraclelinux9

# Set the working directory
WORKDIR /app

# Copy the specific jar file into the container
COPY target/GatewayApp-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
