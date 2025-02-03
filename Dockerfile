FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
LABEL authors="ALEXEY"

ENTRYPOINT ["java", "-jar","/app.jar"]
