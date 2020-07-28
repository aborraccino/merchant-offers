FROM maven:3.6.3-jdk-14 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

RUN mvn package

FROM openjdk:14-jdk-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/merchant-offers-0.0.1-SNAPSHOT.jar /app/merchant-offers-0.0.1.jar
ENTRYPOINT ["java", "-jar", "merchant-offers-0.0.1.jar"]