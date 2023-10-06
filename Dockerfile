# Use a imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim AS build

# Copie o código-fonte e o arquivo pom.xml para o diretório de trabalho no contêiner
WORKDIR /app
COPY . .

# Dê permissão de execução ao arquivo mvnw
RUN chmod +x mvnw

# Execute o Maven para construir o projeto (você pode personalizar isso conforme necessário)
RUN ./mvnw clean package -DskipTests

# Segunda etapa: crie uma imagem menor e copie o arquivo JAR compilado para ela
FROM openjdk:17.0.1-jdk-slim

# Copie o arquivo JAR compilado da etapa anterior
COPY --from=build /Java-Restful-Api/target/Java-Restful-Api.jar /.Java-Restful-Api-0.0.1-SNAPSHOT.jar

# Exponha a porta em que a aplicação será executada
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "/app.jar"]
