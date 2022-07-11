FROM openjdk:8
MAINTAINER r.lanzetta@gmail.com
COPY target/orchestrator*.jar /tmp/orchestrator.jar
CMD ["java", "-jar", "/tmp/orchestrator.jar"]
EXPOSE 8080