FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

## Étape 1 : Étape de construction conditionnelle
#FROM maven:3.8.5-openjdk-17 AS builder
#WORKDIR /app
## Copier les fichiers de configuration Maven et le code source
#COPY pom.xml .
#COPY src ./src
## Copier le dossier target s'il existe, sinon créer un dossier vide
#COPY target target
## Vérifier si un JAR existe dans target, sinon lancer le build Maven
#RUN if [ -f target/*.jar ]; then \
#        echo "JAR found in target, using existing JAR"; \
#        cp target/*.jar app.jar; \
#    else \
#        echo "No JAR found in target, building new JAR"; \
#        mvn clean package -DskipTests; \
#        cp target/*.jar app.jar; \
#    fi
#
## Étape 2 : Créer l'image finale
#FROM openjdk:17-oracle
#VOLUME /tmp
## Copier le JAR depuis l'étape précédente
#COPY --from=builder /app/app.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]