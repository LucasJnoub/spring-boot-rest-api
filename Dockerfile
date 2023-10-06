FROM maven:3.8.5-openjdk-17 AS build
COPY .. /app  # Correção: adicione o diretório de destino /app
WORKDIR /app  # Define o diretório de trabalho no contêiner
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /app/target/Java-Restful-Api-0.0.1-SNAPSHOT.jar /Java-Restful-Api.jar  # Correção: caminho de origem e destino
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Java-Restful-Api.jar"]  # Correção: use colchetes e "java"
