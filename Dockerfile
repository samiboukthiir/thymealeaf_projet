### STAGE 1: Build ###
# Utilisez une image Maven officielle pour construire l'application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn package -Dmaven.test.skip=true

### STAGE 2: Run ###
# Utilisez une image OpenJDK officielle pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copiez le fichier JAR depuis le stage de build
COPY --from=build /app/target/jpa-spring-boot-Thymeleaf-0.0.1-SNAPSHOT.jar app.jar
# Exposez le port sur lequel l'application Spring Boot fonctionne
EXPOSE 8080
# Commande pour exécuter l'application lors du démarrage du conteneur
CMD ["java", "-jar", "app.jar"]
