# Inventario Konex - Full Stack (Spring Boot + Angular + Oracle Docker)

Este repositorio contiene el sistema completo de inventario de
medicamentos de Konex:\
Backend en **Java 17 + Spring Boot (Arquitectura Hexagonal)**, frontend
en **Angular**, y base de datos **Oracle XE** montada con Docker.


## Estructura del Repositorio

    /
    ├─ backend/        → API Rest (Spring Boot, Hexagonal)
    ├─ frontend/       → Aplicación Angular (PrimeNG)
    └─ db/             → Docker Compose con Oracle XE


## Requisitos Previos

-   Java 17
-   Maven 3+
-   Node 20+ y Angular CLI
-   Docker + Docker Compose
-   Navegador
-   Postman o Thunder Client opcional



## Base de Datos (Oracle XE en Docker)

Ubicada en `db/`.

### Levantar la base de datos

``` sh
cd db
docker compose up -d
```

### Apagar

``` sh
docker compose down
```

### Credenciales

-   Host: `localhost`
-   Puerto: `1521`
-   Servicio: `XEPDB1`
-   Usuario: `konex`
-   Password: `konex`


## Backend (Spring Boot - Arquitectura Hexagonal)

### Ejecutar backend

``` sh
cd backend/inventario
mvn spring-boot:run
```

API disponible en:

    http://localhost:8080

Swagger:

    http://localhost:8080/swagger-ui/index.html

### Arquitectura

    backend/
     ├─ domain/
     ├─ application/
     └─ infrastructure/
            ├─ persistence (JPA, adapters)
            ├─ rest (controladores)
            └─ config

### Endpoints principales

**Medicamentos**

    POST    /api/medicamentos
    GET     /api/medicamentos
    GET     /api/medicamentos/{id}
    PUT     /api/medicamentos/{id}
    DELETE  /api/medicamentos/{id}
    POST    /api/medicamentos/{id}/vender

**Ventas**

    GET /api/ventas?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD


## Frontend (Angular)

### Ejecutar frontend

``` sh
cd frontend/konex-inventario-front
npm install
ng serve -o
```

Aplicación:

    http://localhost:4200

### Funcionalidades

-   CRUD de medicamentos
-   Filtros y paginación
-   Registro de ventas
-   Consulta de ventas por rango de fecha
-   UI en Angular + PrimeNG


## Flujo recomendado para probar

1.  Levantar Oracle con Docker.
2.  Ejecutar el backend.
3.  Ejecutar el frontend.
4.  Crear medicamentos → Vender → Revisar ventas.


## Tecnologías Utilizadas

### Backend

-   Java 17
-   Spring Boot 3
-   JPA / Hibernate
-   Oracle XE
-   Arquitectura Hexagonal
-   Lombok\
-   Swagger / OpenAPI

### Frontend

-   Angular
-   PrimeNG
-   Typescript

### Infraestructura

-   Docker / Docker Compose
-   Oracle Database 21c XE


## Autor

Prueba técnica desarrollada por **Esteban Pineda** para *Konex*.
