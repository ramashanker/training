## Build

Build the application as a [Docker](https://www.docker.com/) image using:

```bash
mvn clean install
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
## Run

Run the included docker-compose file with:

```bash
docker-compose up
```

## Generate logs

Application startup will generate some logs.

Calling the demo endpoint will generate some more logs, with Sleuth trace and Span Ids.

## Testing

http://localhost:8080/log/message

## Splunk Query

host=splunkforwarder| regex  message=(?<field>.*(?=Log.*)) | stats   latest(trace) as trace  latest(class) as class latest(message) as message by thread
```