FROM maven:3.8.4-openjdk-17-slim AS build
LABEL authors="diego.agonzalez"
WORKDIR /app

# Copiar el proyecto de la librería común
COPY api-lib-common-artifact /app/api-lib-common-artifact

# Instalar la librería común
RUN mvn clean install -f /app/api-lib-common-artifact/pom.xml

# Copiar el proyecto principal
COPY api-msa-customer-person/pom.xml .
COPY api-msa-customer-person/src ./src

# Construir el proyecto principal
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/api-msa-customer-person-1.0.0.jar app.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar"]