# -------- Build stage --------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

# -------- Runtime stage --------
FROM alpine/java:21-jdk

ENV NODE_ENV="dev"

WORKDIR /app

COPY --from=build /app/target/*.jar weatherapi.jar

EXPOSE 7105

ENTRYPOINT ["java", "-jar", "weatherapi.jar"]