Steps to run kafka tls in local environment:
1) Stop docker container for  "landoop/fast-data-dev"
          docker stop <CONTAINER ID>

2) Start the kafka with SSL enable.
 Using command line:
docker run -d --net=host -e ENABLE_SSL=1 -e SAMPLEDATA=0 -e KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND=true -e LENSES_KAFKA_BROKERS=SSL://0.0.0.0:9093 -e LENSES_KAFKA_SETTINGS_CONSUMER_SECURITY_PROTOCOL=SSL -e LENSES_KAFKA_SETTINGS_PRODUCER_SECURITY_PROTOCOL=SSL -p 2181:2181 -p 3030:3030 -p 9093:9093 -p 9092:9092 -p 8081:8081 -p 9581-9585:9581-9581 landoop/fast-data-dev:latest

Using docker compose:
version: '3'

services:

  lenses-dev:

    image: landoop/fast-data-dev:latest

    ports:

      - "2181:2181"

      - "3030:3030"

      - "9092:9092"

      - "9093:9093"

      - "8081:8081"

    environment:

      - SAMPLEDATA=0

      - ENABLE_SSL=1

      - KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND=true

      - LENSES_KAFKA_BROKERS=SSL://localhost:9093

      - LENSES_KAFKA_SETTINGS_CONSUMER_SECURITY_PROTOCOL=SSL

      - LENSES_KAFKA_SETTINGS_PRODUCER_SECURITY_PROTOCOL=SSL

3) Test kafka ssl 
a) Execute commend in docker container
     docker exec -it <container_id> bash

b)Import trustkey and client key
    root@fast-data-dev / $ wget localhost:3030/certs/truststore.jks

   root@fast-data-dev / $ wget localhost:3030/certs/client.jks

  c) Start producer
  
kafka-console-producer --broker-list localhost:9093 --topic test.topic \
--producer-property bootstrap.servers=localhost:9093 \
--producer-property security.protocol=SSL \
--producer-property ssl.keystore.location=client.jks \
--producer-property ssl.keystore.password=fastdata \
--producer-property ssl.key.password=fastdata \
--producer-property ssl.truststore.location=truststore.jks \
--producer-property ssl.truststore.password=fastdata



d) Start consumer 
kafka-console-consumer --bootstrap-server localhost:9093 --topic test.topic \
--consumer-property bootstrap.servers=localhost:9093 \
--consumer-property security.protocol=SSL \
--consumer-property ssl.keystore.location=client.jks \
--consumer-property ssl.keystore.password=fastdata \
--consumer-property ssl.key.password=fastdata \
--consumer-property ssl.truststore.location=truststore.jks \
--consumer-property ssl.truststore.password=fastdata



     
Data should get published on topic.





4) start lh component with ssl configuration enable.
 a)  Download the truststore.jks and client.jks file to local machine from url : http://localhost:3030/certs/


Copy the certificates to 

src/main/resources/ssl/truststore.jks
src/main/resources/ssl/client.jks
     

b) configure the property file for ssl configuration:
ssl:
  truststore-location: file:/C:/security/truststore.jks(downloaded path)
  truststore-password: fastdata
  keystore-location: file:/C:/security/client.jks (downloaded path)
  keystore-password: fastdata
  key-password: fastdata
c) Start the component with VM options:
      -Dspring.profiles.active=localssl 

5) Revert back to non ssl mode:
List the docker landoop container.



docker stop <CONTAINER ID>

docker restart $(docker ps -a -q)