FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/jukes-0.0.1.jar /app
EXPOSE 8080
CMD ["java", "-jar", "jukes-0.0.1.jar"]

