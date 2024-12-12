FROM openjdk:8-jdk-slim
WORKDIR /app
COPY target/SHAMAN_JUNIOR-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080