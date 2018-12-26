FROM openjdk:8-jdk-alpine

RUN mkdir /work
COPY ./build/libs/*.jar /work/app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/work/app.jar"]
