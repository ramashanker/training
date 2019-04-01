# Project
This Apllication is use understand the basic kafka message producer in sprict boot application.

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
$ docker-compose -f docker-compose-dependency.yml pull
$ docker-compose -f docker-compose-dependency.yml up
```
### Running the application using Kubernetes.

```
kubectl create -f kafka-application.yml
kubectl get pods
kubectl get deployment
kubectl describe deployments kafka-deployment
docker ps
kubectl expose deployment kafka-deployment --type=LoadBalancer --name=my-service
kubectl get services my-service
kubectl describe services my-service
```

## Swagger 

Added the swagger api to detail descrition of the rest api exposed fir the application.
The URL for the swagger documentation is:
http://ipaddress:port/swagger-ui.html
Example
http://localhost:8083/swagger-ui.html

## Testing
 1)Publish using default topic test.topic
 
 ```
curl -X POST 'http://127.0.0.1:9095/publish/data?message=mydata'
 ```

## Command line execution

1) Enter to container

	´´´
	docker exec -it <container Id> bash
	Ex : docker exec -it 1d6716fa6b56 bash

	´´´
2) Look for topics created

	´´´
	kafka-topics --zookeeper localhost --topic  <TAB><TAB>

	´´´
3) configure  topic
    
    ´´´
	kafka-topics --config <TAB><TAB>

	´´´
4) consumer

	kafka-console-consumer -- <TAB><TAB>


