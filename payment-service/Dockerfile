FROM maven:3.8.1-openjdk-8 AS builder1
WORKDIR /app3
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:8-jdk-slim
WORKDIR /app3
COPY --from=builder1 /app3/target/*jar-with-dependencies.jar app3.jar
EXPOSE 65534
ENTRYPOINT ["java","-jar","app3.jar"]

