# TelemedicineRestAPI

API REST in Java for the communication between an application an a MySQL data base.
The API recieves https which are use to search, update etc data from the database and produces a response which is send as a JSON string, the application gets the JSON repsonse and converts it using the library Gson to an APIRespose object then it is shown to the user the error message if it is an error, or continues with the normal execution if it is not.

## Intallation

This project requires the addition of three .jar to work.

1. gson .jar
2. json .jar
3. mysql-connector-java .jar

Add them to the project build path
