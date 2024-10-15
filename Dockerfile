FROM openjdk:17-jdk-slim
EXPOSE 6262
ARG JAR_FILE=target/*.jar
COPY ./target/TransactionAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]