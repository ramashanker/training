# Project
This Apllication is use to upload json file in mongo database using multipart file uploader.

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
$ docker-compose -f docker-compose-mongo.yml pull
$ docker-compose -f docker-compose-mongo.yml up
```

## Swagger 

Added the swagger api to detail descrition of the rest api exposed fir the application.
The URL for the swagger documentation is:
http://ipaddress:port/swagger-ui.html
Example
http://localhost:8083/swagger-ui.html

## Testing

Once the application is up and running.You can wither use post man to hit the application or curl command.

Postman:
 put :http://localhost:8081/entryfile?name=data
 Body file :attachement file

Curl:
PUT /entryfile?name=data HTTP/1.1
Host: localhost:8081
Cache-Control: no-cache
Postman-Token: 1c5628b9-88e2-4124-9cf4-b7aced6c155a
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="SuspiciousEntity.json"
Content-Type: application/json


------WebKitFormBoundary7MA4YWxkTrZu0gW--
