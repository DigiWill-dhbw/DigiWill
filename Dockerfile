FROM openjdk:8-jdk-alpine
LABEL io.portainer.uac.public=true

ENV envTarget=deploy
ENV VIRTUAL_HOST=digiwill.robinkuck.de,www.digiwill.robinkuck.de
ENV VIRTUAL_PORT=8080
ENV LETSENCRYPT_HOST=digiwill.robinkuck.de
ENV LETSENCRYPT_EMAIL=kuck.robin@gmail.com
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
