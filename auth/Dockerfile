FROM openjdk:21-jdk
COPY target/apiGateway-0.0.1-SNAPSHOT.jar auth.jar
ENTRYPOINT ["java", "-jar", "auth.jar"]
