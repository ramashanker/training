# Project
This Apllication is use to import json file data from your local system to mongo collection and export 
from mongo collection to your local file system.

## Import project
Import the project on either eclipse or inellij as a maven import project

## Building
This project is maven project which will be build using maven command.

```bash
$ mvn clean install
```

## Dockerization
Here i am using maven spotify plugin to create the docker image for this application.
Use the below command to create the docker image.
For creating please use your repository to create the image which will be easy to push image in your docker hub.
update in your pom.xml.
<docker.image.prefix> <your repo name> </docker.image.prefix>

```bash
$ mvn install dockerfile:build
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
$ docker-compose -f docker-compose-nosql.yml pull
$ docker-compose -f docker-compose-nosql.yml up
```

## Swagger 

Added the swagger api to detail descrition of the rest api exposed fir the application.
The URL for the swagger documentation is:
http://ipaddress:port/swagger-ui.html
Example
http://localhost:8083/swagger-ui.html

## Testing
