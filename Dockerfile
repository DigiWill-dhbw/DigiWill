FROM openjdk:8-jdk-alpine
ENV envTarget=deploy
ENV VIRTUAL_HOST=digiwill.robinkuck.de,www.digiwill.robinkuck.de
ENV VIRTUAL_PORT=8080
EXPOSE 8080
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]