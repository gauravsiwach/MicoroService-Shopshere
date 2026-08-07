# API Gateway Dockerfile
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY common-security-0.0.1-SNAPSHOT.jar /tmp/common-security-0.0.1-SNAPSHOT.jar
COPY src ./src
RUN mvn install:install-file -Dfile=/tmp/common-security-0.0.1-SNAPSHOT.jar -DgroupId=com.example -DartifactId=common-security -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
