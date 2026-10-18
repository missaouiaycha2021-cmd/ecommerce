# Étape 1 : compilation avec Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image finale légère
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN chgrp -R 0 /app && chmod -R g=u /app
USER 1001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

#CMD ["python", "app.py"] Docker mémorise la commande (pyhton app.py)à lancer lorsque le conteneur démarre.Si ne met pas cette commande alors le container s'arreter car ne trouve pas la commande pour lancer l'app.

#CMD=remplacement
#ENTRYPOINT =ajout d'arguments
