# Etapa de construcción
FROM maven:3.8.4-openjdk-17-slim AS build
LABEL authors="diego.agonzalez"
WORKDIR /app

# Copiar el proyecto de la librería común
COPY api-lib-common-artifact /app/api-lib-common-artifact

# Instalar la librería común
RUN mvn clean install -f /app/api-lib-common-artifact/pom.xml

# Copiar el proyecto principal
COPY api-msa-account-movements/pom.xml .
COPY api-msa-account-movements/src ./src

# Construir el proyecto principal
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/api-msa-account-movements-1.0.0.jar app.jar
EXPOSE 8002
ENTRYPOINT ["java","-jar","app.jar"]