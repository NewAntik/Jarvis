FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ../build/libs/Jarvis-0.0.1-SNAPSHOT.jar /app/Jarvis.jar
COPY photos /app/photos
CMD ["java", "-jar", "/app/Jarvis.jar"]