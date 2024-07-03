FROM openjdk:17-alpine

EXPOSE 8080

COPY ./build/libs/*.jar app.jar

COPY ./src/main/resources/theyounghana-ff6c3-firebase-adminsdk-xsbw0-b4fd60e217.json /src/main/resources/theyounghana-ff6c3-firebase-adminsdk-xsbw0-b4fd60e217.json

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]