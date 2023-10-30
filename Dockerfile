FROM openjdk:17-alpine
COPY target/talk-and-travel-app.jar talk-and-travel-app.jar
ENTRYPOINT ["java","-jar","talk-and-travel-app.jar app"]
