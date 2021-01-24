# Installation
## Docker
To install this application you have to install Docker in order to run postgreSQL which is located in docker-compose.yml
More info can be find here https://docs.docker.com/get-docker/

## Build
In console type
```mvn clean install```
in order to build application.

## Run
Go to the root directory of the application and:
1. To run PostgreSQL type ```docker-compose up -d``` - you can also check whether postgres is running by listening 
   docker containers ```docker ps```
1. To run application type ```java -jar target/interview-0.0.1-SNAPSHOT.jar```
