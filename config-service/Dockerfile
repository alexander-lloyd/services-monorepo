FROM openjdk:11
VOLUME /tmp
ADD target/config-service.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
