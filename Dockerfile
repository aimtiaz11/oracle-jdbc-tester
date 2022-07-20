FROM maven:3.6.0-jdk-11-slim as build
ADD ./pom.xml pom.xml
ADD ./src src/
RUN mvn clean package

FROM openjdk:11-jre-slim-buster
COPY --from=build target/jdbc-tester-1.1.jar jdbc-tester-1.1.jar
