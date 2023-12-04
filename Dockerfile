FROM maven:3.8.3-openjdk-17-slim AS Builder

WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/

RUN mvn clean package

COPY target/pricefinder-0.0.1-SNAPSHOT.jar target/application.jar
FROM openjdk:17-oracle
WORKDIR /app/
COPY --from=builder /build/target/application.jar /app/

CMD java -jar /app/application.jar