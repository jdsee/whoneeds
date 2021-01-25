### STAGE 1: Build and test ###
FROM gradle:6.7.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ls -la
RUN gradle build --debug


### STAGE 2: Run application ###
FROM openjdk:8-jre-slim
MAINTAINER Joscha Seelig
EXPOSE 9000
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/whoneeds-api.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/whoneeds-api.jar"]
