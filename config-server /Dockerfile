# Config Server Dockerfile
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY config-repo ./config-repo
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/config-repo ./config-repo
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]
