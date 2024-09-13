FROM openjdk:17-jdk-slim

EXPOSE 5500

COPY build/libs/MyNewServiceMoneyTransfer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]