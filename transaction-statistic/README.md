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
$ docker-compose pull
$ docker-compose up
```

## Swagger 

Added the swagger api to detail descrition of the rest api exposed for the application.
The URL for the swagger documentation is:
http://ipaddress:port/swagger-ui.html
Example
http://localhost:8080/swagger-ui.html

## Testing
Once the application is up and running.You can wither use post man to hit the application or curl command.

### Create Transaction data.

curl -X POST \
  http://localhost:8080/transactions \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 511cb43a-7098-4daa-b2f4-6e8295827987' \
  -d '{
  "amount": "18.3343",
  "timestamp": "2018-10-13T03:34:30.000Z"
}'

### Get Statistical data for 60 seconds.

curl -X GET \
  http://localhost:8080/statistics \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: ee0c8f86-0e52-4c97-852e-61414b3e9a57' 
  

### Delete transaction data or clean up the data.

curl -X DELETE \
  http://localhost:8080/transactions \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: bb32fa2a-f8d1-485c-9b28-02f6b6dbc570'
  
### PostMan Test  
Through Postman import the collection json to the post man:
N26-code-challange.postman_collection.json  
  
 
## Design document.

 Here i have exposed the three rest url which are.
 1) Create Transaction data
 	Post:http://localhost:8080/transactions
 2) Get statistics data
     Get: http://localhost:8080/statistics
 3) Delete Transaction data;
    Delete: http://localhost:8080/transactions
### Create Transaction Data

 While creating the transaction data i am receiving the json data as:
 {
  "amount": "18.3343",
  "timestamp": "2018-10-13T03:34:30.000Z"
 }
Where this data storing in to ConcurrentNavigableMap where key is timestamp in millisecond and value as List<Transaction>.
Transaction is pojo for request json.
After invoking the create transaction here i am cleaning the older data which have timestamp more than 60.
For this created the thread executor service with two thread it will execute while create the transaction data and another and another when retrieving the statistics.

### Get Statistics Data

To get statistics i am using the java-8 statistics feature.Since BigDecimal is not supported by java library i have created my own functionality for 
BigDecimal statistics.
The classes which created are:
BigDecimalSummaryStatistics
MyCollectors
ToBigDecimalFunction
Here i am mimicking the same approach which are provided for DoubleSummaryStatistics as well as LongSummaryStatistics.
Once i receive the BigDecimal Statistics result i am cleaning up the ConcurrentNavigableMap which have data older than 60 second.Therefore the data should not blow up with any operation executing continuously.

### Delete Transaction data

In this request i am simply cleaning of the entire data from ConcurrentNavigableMap.
The reason for using ConcurrentNavigableMap is it contain the data in sorted order and provide the utility to head or tail data from any precise range or index.

I have used java8 statistics feature since its full fill our requirement.

### Clean up data
@Async
	private void cleanupCache(long startingESecond) {
		transactionCache.entrySet().removeIf(entry -> entry.getKey().compareTo(Long.valueOf(startingESecond)) <= 0);

	}





