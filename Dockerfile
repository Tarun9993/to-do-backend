FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM eclipse-temurin:21-jdk
COPY --from=build /target/to-do-0.0.1-SNAPSHOT.jar to-do.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/to-do.jar"]