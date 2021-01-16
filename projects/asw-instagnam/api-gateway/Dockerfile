###########################################################
# Dockerfile per l'applicazione sentence / sentence service 
###########################################################

FROM openjdk:8-jdk 

ADD build/libs/api-gateway.jar api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "api-gateway.jar"]
