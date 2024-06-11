FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

RUN ./mvnw bootJar --no-daemon

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=target /target/demo-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]