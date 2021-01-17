FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /workspace/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=default
ENV JDBC_DATABASE_URL=""
ENV JDBC_DATABASE_USERNAME=""
ENV JDBC_DATABASE_PASSWORD=""
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]