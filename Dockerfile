FROM openjdk:8-jdk-alpine

MAINTAINER Zeynel Acar
EXPOSE 8090
ADD target/creditmanagement-0.0.1-SNAPSHOT.jar creditmanagement.jar

ENTRYPOINT ["java","-jar","creditmanagement.jar"]