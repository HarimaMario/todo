# Fase de compilación
FROM maven:3.8.2-openjdk-11-slim AS build
ARG SPRING_PROFILES_ACTIVE
ARG MONGO_HOST
ARG MARIA_HOST
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV MONGO_HOST=${MONGO_HOST}
ENV MARIA_HOST=${MARIA_HOST}
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

# Fase de ejecución
FROM openjdk:11-jre-slim-buster
COPY --from=build /usr/src/app/target/todo-0.0.1-SNAPSHOT.jar /usr/app/todo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/todo-0.0.1-SNAPSHOT.jar"]