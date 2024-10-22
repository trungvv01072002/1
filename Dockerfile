#FROM openjdk:17
#WORKDIR /app
#ARG JAR_FILE=target/Management-user-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#COPY .env.dev .env
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM openjdk:17
WORKDIR /app
ARG JAR_FILE=target/Management-user-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY .env.dev .env
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]