FROM openjdk:8-jdk-alpine

MAINTAINER Zeynel Acar
EXPOSE 8080
ADD target/creditmangement-0.0.1-SNAPSHOT.jar creditmangement.jar

ENTRYPOINT ["java","-jar","creditmanagement.jar"]