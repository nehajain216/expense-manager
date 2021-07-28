FROM openjdk:11-jdk-slim
COPY target/expense-manager-api-1.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
