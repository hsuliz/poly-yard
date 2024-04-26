#TODO use gradle for it
FROM amazoncorretto:21-alpine
EXPOSE 8080
ARG JAR_FILE=build/libs/book-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]