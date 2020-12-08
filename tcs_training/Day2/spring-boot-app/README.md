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
wouterd
alexec
spotify
fabric8io

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
$gradle bootRun
```

### Running the application in docker container

For that we need to create the docker image first and then we can either use docker-compose-mongo.yml or using docker run command.
Start with docker compose please update the docker-compose.yml.
image: <your repo name>/<app-name>:latest
Command to start:

```bash
$ docker-compose  pull
$ docker-compose  up
```
### Running the application using Kubernetes.

```
microk8s kubectl create -f application-deployment.yml
microk8s kubectl get pods
microk8s kubectl get deployment
microk8s kubectl describe deployments spring-boot-deployment
docker ps
microk8s kubectl cluster-info
microk8s kubectl expose deployment spring-boot-deployment --type=LoadBalancer --name=data-service
microk8s kubectl get services data-service
microk8s kubectl describe services data-service
```

#### Debug in K8
```
kubectl delete deployment spring-boot-deployment
kubectl delete -f application-deployment.yml
sudo microk8s kubectl delete svc data-service
kubectl describe pods <pod-name>
```
####Docker image Push
```
docker save falcon007/spring-boot-app  > springapp.tar
microk8s ctr image import springapp.tar
microk8s ctr image list

```
####Microk8s
```
Convert docker-compose to deployement yml

kompose convert -f docker-compose.yml

sudo snap install microk8s — classic
sudo microk8s.inspect
sudo microk8s.start
sudo microk8s.status
sudo microk8s.enable dashboard prometheus storage
sudo microk8s.kubectl get all --all-namespaces
microk8s.kubectl apply -f ./config/
sudo microk8s.kubectl get all --all-namespaces
microk8s.kubectl logs pod/sprigapp-546448dfc5-h5hdv
microk8s.kubectl delete -f ./config/

DashBoard Token:
token=$(microk8s kubectl -n kube-system get secret | grep default-token | cut -d " " -f1)
microk8s kubectl -n kube-system describe secret $token

https://medium.com/@iskitsas/autoscale-a-java-cms-app-with-kubernetes-the-microk8s-approach-from-docker-to-kubernetes-7c021f7d8333
Grafana 
username:admin
password:admin
```

## Swagger 

Added the swagger api to detail descrition of the rest api exposed fir the application.
The URL for the swagger documentation is:
http://ipaddress:port/swagger-ui.html
Example
http://localhost:8083/swagger-ui.html
curl -X GET http://10.152.183.166:8082/
## Testing

http://localhost:8080/log/message

## Splunk Query

host=splunkforwarder| regex  message=(?<field>.*(?=Log.*)) | stats   latest(trace) as trace  latest(class) as class latest(message) as message by thread