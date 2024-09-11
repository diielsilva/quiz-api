# Build Stage
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /build
COPY guess-the-number/pom.xml .
RUN mvn dependency:go-offline
COPY guess-the-number/src ./src
RUN mvn clean package -DskipTests

# Runtime Stage
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
CMD java -jar app.jar