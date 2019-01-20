FROM openjdk:8-jdk-alpine
MAINTAINER vicente.nvt@gmail.com
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/urlShortener-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} urlShortener.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/urlShortener.jar"]