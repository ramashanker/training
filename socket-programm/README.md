# Project
This Apllication is use understand the basic security mechanism in sprict boot application.

## Import project
Import the project on either eclipse or inellij as a maven import project

## Building

### Maven
This project is maven project which will be build using maven command.

```bash
$ mvn clean install
```

### Gradle

```bash
$ gradle build
```

## Dockerization

### Maven
Here i am using maven spotify plugin to create the docker image for this application.
Use the below command to create the docker image.
For creating please use your repository to create the image which will be easy to push image in your docker hub.
update in your pom.xml.
<docker.image.prefix> <your repo name> </docker.image.prefix>

```bash
$ mvn install dockerfile:build
```

### Gradle

Here i am using gradle  gradle.plugin.com.palantir.gradle.docker plugin to create the docker image for this application.
Use the below command to create the docker image.
For creating please use your repository to create the image which will be easy to push image in your docker hub.
update in your build.gradle group name with repository name.
group <repository-name>

```bash
$ gradle build docker
```

### Push docker images

```bash
docker login
$ docker push <repository-name>/springsecurity
```

## Running

The application can be start is either locally using maven or start in docker container.

### Running the application in local environment
Using eclipse:It can be run as java application or run configuration to provide the program arguments.
You can run using maven command as:

```bash
$ mvn spring-boot:run
```

### Running the application in docker container

For that we need to create the docker image first and then we can either use docker-compose-mongo.yml or using docker run command.
Start with docker compose please update the docker-compose-mongo.yml.
image: <your repo name>/mongodboperation:latest
Command to start:

```bash
$ docker run --name smppserver -it falcon007/server-app:1.0.0
$ docker run --link smppserver -it falcon007/client-app:1.0.0
```
## Testing
