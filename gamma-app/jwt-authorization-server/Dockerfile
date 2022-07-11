FROM openjdk:8
MAINTAINER r.lanzetta@gmail.com
COPY target/jwt-authorization-server*.jar /tmp/jwt-authorization-server.jar
CMD ["java", "-jar", "/tmp/jwt-authorization-server.jar"]
EXPOSE 9999