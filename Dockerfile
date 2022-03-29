FROM maven:3.8-openjdk-17 AS build
COPY --chown=maven:maven . /home/maven/src
WORKDIR /home/maven/src
RUN maven clean build -Dmaven.test.skip=true

FROM openjdk:16-alpine
EXPOSE 8080
COPY --from=build /home/maven/src/target/classes/*.jar application.jar
ENTRYPOINT ["java", "-jar", "-Xmx4g", "application.jar"]