# Etapa de build
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app
COPY springboot-modulo/ ./springboot-modulo/
WORKDIR /app/springboot-modulo

# 👇 Esto asegura que mvnw tenga permisos de ejecución (por si acaso)
RUN chmod +x mvnw

# Construye el jar (sin tests para acelerar)
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia el JAR construido desde la etapa anterior
COPY --from=build /app/springboot-modulo/target/*.jar app.jar

# Expón el puerto (ajusta si usas otro)
EXPOSE 8080

# Comando para ejecutar tu app Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
