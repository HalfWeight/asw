FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/connessioni.jar
COPY ${JAR_FILE} app.jar
ENV SERVER_PORT=8080
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-Xms64m", "-Xmx128m", "-jar","/app.jar"]