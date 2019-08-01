# Use an official openjdk runtime as a parent image
FROM openjdk:8-jdk-alpine
# bind a volume to /tmp
VOLUME /tmp
#argument for the JAR_File
ARG JAR_FILE
#Copy the jarfile contents into the container at app.jar
COPY ${JAR_FILE} app.jar
#Entrypoint for the app.jar file to start.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]