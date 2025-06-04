# Build stage
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY springboot-modulo/ ./springboot-modulo/
WORKDIR /app/springboot-modulo
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/springboot-modulo/target/*.jar app.jar
ENV PORT 8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
