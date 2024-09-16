FROM openjdk:17-oracle AS extracted
WORKDIR /app
COPY ./build/libs/Jarvis-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

