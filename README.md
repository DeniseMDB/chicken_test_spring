# Proyecto de simulacro de manejo de una granja

## Descripción

La aplicación "Farm" fue desarrollada en Spring Boot, permite gestionar una granja, incluyendo operaciones como la compra y venta de animales, gestión de recursos, y generación de informes. Además, ofrece una funcionalidad de simulación del paso del tiempo, donde la velocidad del paso de los días se ingresa por parámetro en segundos. Esta simulación refleja cambios en el estado de las gallinas y los huevos: las gallinas ponen huevos, envejecen, mueren, los huevos se convierten en gallinas, y se regula el stock para no alcanzar la capacidad máxima de la granja. La aplicación está diseñada para ser fácilmente extensible y ofrece una interfaz amigable utilizando Thymeleaf para el front-end y Swagger/OpenAPI para la documentación de la API.


<img width="872" alt="Front-Farm" src="https://github.com/user-attachments/assets/52fcad04-e414-45e0-b6f9-713c408b668c">

## Características

- Gestión de granjas, animales y recursos.
- Operaciones CRUD para entidades clave.
- Generación de informes en formato CSV.
- Autenticación y autorización utilizando Basic Auth.
- Front-end basado en Thymeleaf y Bootstrap.
- Base de datos en memoria H2 para desarrollo y pruebas.
- **Persistencia de datos**: Uso de JPA/Hibernate para la persistencia y manejo de datos.
- **Programación Orientada a Objetos (POO)**: Diseño basado en principios de POO.
- **Seguridad**: Implementación de seguridad con Spring Security.
- **Documentación**: Integración con Swagger para la documentación y prueba de la API.
- **Manejo de Hilos**: Gestión de hilos para la simulación de actividades en la granja.
- **Manejo de Excepciones**: Implementación de mecanismos para el manejo adecuado de errores.

## Tecnologías Utilizadas

- **Spring Boot 3.3.1**: Framework principal para el desarrollo de la aplicación.
- **Spring Data JPA**: Para operaciones de persistencia de datos.
- **Spring Security**: Para la gestión de seguridad y autenticación.
- **Thymeleaf**: Motor de plantillas para el front-end.
- **Bootstrap**: Framework de CSS para un diseño responsivo y atractivo.
- **OpenCSV**: Para la generación y manipulación de archivos CSV.
- **H2 Database**: Base de datos en memoria utilizada para el desarrollo.
- **Swagger/OpenAPI**: Para la documentación de la API.
- **Docker**: Para la creación de contenedores de la aplicación.


Denise Du Bois
