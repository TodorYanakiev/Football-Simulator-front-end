FROM openjdk:22
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=build/libs/FootballManager-front-end-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]