FROM openjdk:11

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} spring-31.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/spring-31.jar"]