FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY build/libs/DigitalActive-0.0.1-SNAPSHOT.jar app.jar
COPY .env .env
EXPOSE 8080
EXPOSE 5677
EXPOSE 15677
ENTRYPOINT ["java", "-jar", "app.jar"]
