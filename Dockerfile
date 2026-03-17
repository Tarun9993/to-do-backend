FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM eclipse-temurin:21-jdk
COPY --from=build /target/Email-Writer-0.0.1-SNAPSHOT.jar Email-Writer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/Email-Writer.jar"]