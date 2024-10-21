# API MSA BPT Pichincha Assessment

Este proyecto contiene dos microservicios Spring Boot: `api-msa-bpt-customer-person` y `api-msa-bpt-account-movements`. Ambos utilizan una base de datos PostgreSQL y están configurados para ejecutarse en contenedores Docker.

## Estructura del Proyecto

- `api-msa-bpt-customer-person`: Microservicio para gestionar información de clientes.
- `api-msa-bpt-account-movements`: Microservicio para gestionar movimientos de cuentas.
- `api-lib-bpt-common-artifact`: Librería común utilizada por ambos microservicios.

## Tecnologías Utilizadas

- Java 17
- Spring Boot 2.7.1
- Spring WebFlux
- R2DBC
- Flyway
- PostgreSQL
- Docker
- Maven

## Configuración

Cada microservicio tiene su propio archivo `application.yml` con configuraciones específicas. Los principales aspectos configurables incluyen:

- Puertos de los servicios
- Conexiones a la base de datos
- Configuración de Flyway
- Logging

## Construcción y Ejecución

### Usando Docker

1. Asegúrate de tener Docker instalado.
2. Navega al directorio raíz del proyecto.
3. Ejecuta:

docker-compose up --build


Esto construirá y ejecutará ambos microservicios y la base de datos PostgreSQL.

### Desarrollo Local

Para ejecutar los servicios localmente:

1. Asegúrate de tener Java 17 y Maven instalados.
2. Navega al directorio de cada microservicio.
3. Ejecuta:

mvn spring-boot:run


## Pruebas

El proyecto incluye pruebas unitarias e integración. Para ejecutar las pruebas: mvn test


## Endpoints

Se incluye collection de postman en docs/collections


## Contribución

Para contribuir al proyecto:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tus cambios.
3. Realiza tus cambios y asegúrate de que todas las pruebas pasen.
4. Envía un pull request con una descripción detallada de tus cambios.

## Licencia

MIT
