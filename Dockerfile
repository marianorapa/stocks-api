FROM adoptopenjdk/openjdk11:alpine
COPY target/*.jar api.jar
CMD ["java", "-jar", "api.jar"]