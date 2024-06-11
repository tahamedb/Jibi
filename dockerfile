# First stage: build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second stage: run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/JibiAPI-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]