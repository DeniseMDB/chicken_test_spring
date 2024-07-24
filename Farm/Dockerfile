# Usa una imagen base de JDK para ejecutar la aplicación
FROM openjdk:21-jdk

COPY Farm/target/Farm-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Farm-0.0.1-SNAPSHOT.jar"]