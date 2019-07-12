# Set Up Kafka
##set zookeeper on local system
	ZOOKEEPER_HOME=C:\Apache\zookeeper-3.4.10
	path add %ZOOKEEPER_HOME%\bin
## start zookeeper server
```
>zkserver

```

## Start Kafka server

```
>.\bin\windows\kafka-server-start.bat .\config\server.properties

```
### Create Topic on kafka
```
bin\windows>\bin\windows
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic log-stream

```
### Test producer and consumer on kafka

```
bin\windows>kafka-console-producer.bat --broker-list localhost:9092 --topic log-stream

bin\windows>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic log-stream

```
## Start microservice

```
>mvn spring-boot:run

```

