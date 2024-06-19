FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src/ src/
RUN mvn package


FROM openjdk:21-ea-31-slim
WORKDIR /app
COPY src/main/resources/static .
COPY --from=build /app/target/Edem-Backend-1.0.jar .
CMD ["java", "-jar", "Edem-Backend-1.0.jar"]