FROM openjdk:13.0.2

VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
