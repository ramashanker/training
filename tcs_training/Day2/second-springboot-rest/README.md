# Project
This Apllication is use understand the basic security mechanism in sprict boot application.

## Import project
Import the project on either eclipse or inellij as a maven import project

## Building

### Maven
This project is maven project which will be build using maven command.

```bash
$ mvn clean install
$ mvn spring-boot:run

```

### Test
curl -X GET http://localhost:8088/get 
curl -X POST http://localhost:8088/post -d '{"name": "rama", "role": "developer"}' -H 'Content-type:application/json'
curl -X PUT http://localhost:8088/put/shanker -H 'content-type: application/json' -d '{"name": "Rama", "role": "developer"}'
curl -X DELETE http://localhost:8088/delete/rama 
curl -X GET http://localhost:8088/path/rama
curl -X GET 'http://localhost:8088/request?name=test1' 