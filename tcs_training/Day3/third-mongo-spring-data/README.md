# training
Post:http://localhost:8080/customer/create?name=shanker&custId=12&address=bangalore
Get Read all:http://localhost:8080/customer/read
Delete by name:http://localhost:8080/customer/delete?name=shanker

{
  "name": "Rama",
  "custId": "1re206",
  "address": "Bangalore"
}

Show data::::::

docker exec -it day3_mongo_1 bash
mongo
show dbs
show tables
db.customer.find()

