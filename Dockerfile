FROM openjdk:8-jdk-alpine
LABEL maintainer="jeffreybrettcoleman@protonmail.com"
VOLUME /tmp
EXPOSE ${PORT}
ARG JAR_FILE
ADD ${JAR_FILE} openliberty-spring-rxjava.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/openliberty-spring-rxjava"]
