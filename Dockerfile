FROM openjdk:17-alpine
COPY target/talk-and-travel-app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
