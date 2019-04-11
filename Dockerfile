FROM openjdk:8-jdk-alpine
EXPOSE 8080
ENV envTarget=dev
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]