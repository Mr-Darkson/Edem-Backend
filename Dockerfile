FROM openjdk:21-ea-31-slim
WORKDIR /app
COPY target/Edem-Backend-1.0.jar .
CMD ["java", "-jar", "Edem-Backend-1.0.jar"]