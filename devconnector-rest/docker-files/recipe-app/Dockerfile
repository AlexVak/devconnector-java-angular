FROM maven:3.6-jdk-8 AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests=true

FROM openjdk:8-jdk-alpine
ENV MYSQL_DB_PASSWORD password
VOLUME /tmp
COPY --from=builder /usr/src/app/target/spring5-recipe-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.datasource.password=${MYSQL_DB_PASSWORD}"]