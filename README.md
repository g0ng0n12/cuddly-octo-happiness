# KMarket
-----------

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/es/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)
- [DOCKER](https://docs.docker.com/install/) - If you want to build and run a release with Mysql

----------------------
# Endpoints

## Swagger-UI
- http://localhost:8090/swagger-ui/#

## App Health-Check
- http://localhost:8090/actuator/health
- 
## Postman
- KMARKET.postman_collection.json file... this file is a Postman Collection v2.1

## Checkout Enpoint
- POST - http://localhost:8090/api/v1/carts/checkout
- RequestBody Example
  - {
       "products" : ["SR1","CF1","GR1"]
    }
----------------------
## Running the application locally

There are several ways to start the application Locally

For development purposes you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
in the command line from the root path of the project run:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=develop
```
This will fire up the application with an H2 on-memory database loaded. When you start the app you can access the
h2 db using this url: http://localhost:8090/h2-console
the user and pass are the default

```
to check the status of the application please use this url: http://localhost:8090/actuator/health

to see the API endpoints documentation you please check the swagger ui URL: http://localhost:8090/swagger-ui/#
```
-------------

## Build and Run a Release with Docker and Docker-compose in your LocalHost

You can use docker-compose to load the app with a MYSQL db.
All the environment variables for docker were place in the .env-sample file.
To generate a build and run with docker you need to create a .env file and copy and paste the vars from the .env-sample
file into the new .env file

in the command line from the root path of the project run:
```shell
docker-compose up
```

```
to check the status of the application please use this url: http://localhost:8090/actuator/health
to see the API endpoints documentation you please check the swagger ui URL: http://localhost:8090/swagger-ui.html
```

if you need to re-run (without delete and stop all the process) the build and deploy
new changes you can run in the command line from the root path:
```shell
docker-compose up --build
```

in order to stop the containers you should run in the command line from the root path:
```shell
docker-compose down --rmi local
```
this will stop the containers and remove the images from your computer.
### Other Docker tips

When you use the build using the docker-compose file you will see that for every build you are going to generate
a new image... so in order to delete the unused image please use this command:

```shell
docker image prune -f
```

-------------------------

## Running the Integration Tests
In This case the Integration tests lifts all the spring context, and the tests are in the KmarketApplicationIT File
I added one integration test that contains the special conditions required
- The CEO is a big fan of buy-one-get-one-free offers and of green tea. He wants us to add a rule to do this
- The COO, though, likes low prices and wants people buying strawberries to get a price discount for bulk purchases. If you buy 3 or more strawberries, the price should drop to £4.50 per strawberry.
- The CTO is a coffee addict. If you buy 3 or more coffees, the price of all coffees should drop to two thirds of the original price.

Also the Integration tests runs the unit tests.
in order to run the integration tests you should run in the command line from the root path:
```shell
 mvn -DSPRING_PROFILE_ACTIVE=integration-test integration-test verify  
```
### When integration-test ends you will see this.
```
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 8.385 s - in com.fx.kmarket.it.KmarketApplicationIT
```

## Running Unint Tests
In this case the Unit tests are in the CartServiceTest File
In order to run Unit Tests with mocks you should run in the command line from the root path:
```shell
mvn -DSPRING_PROFILE_ACTIVE=test test
```
When Finished OK you will see this:
```shell
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

## Common Issues
    1. When you run docker-compose up... if you see warnings like this: WARN[0000] The "MYSQL_USER" variable, you should create the .env file
    2. we need to improve the Docker Creation for the Release, since we're having SQL issues.
