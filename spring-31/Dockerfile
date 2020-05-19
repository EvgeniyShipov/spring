FROM openjdk:11

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} spring-31.jar

ENTRYPOINT ["java", "-jar", "/spring-31.jar"]