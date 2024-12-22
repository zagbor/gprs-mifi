FROM maven:3.8.1-openjdk-8 AS builder
WORKDIR /app1
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:8-jdk-slim
WORKDIR /app1
COPY --from=builder /app1/target/*jar-with-dependencies.jar app1.jar
EXPOSE 65535
ENTRYPOINT ["java","-jar","app1.jar"]

