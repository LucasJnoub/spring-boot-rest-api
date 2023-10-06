# Use a imagem base do OpenJDK 17
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app
COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim

COPY --from=build /Java-Restful-Api/target/Java-Restful-Api-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]
