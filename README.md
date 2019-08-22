# Star Wars API

Simple API that permits to register planets of Star Wars Universe.

API Documentation: [Click here](https://documenter.getpostman.com/view/216408/SVfKwqaf?version=latest#3529976c-1f16-4940-9df6-4b0e839e483a)

You can use cUrl or Postman (https://www.getpostman.com/) to access the API.

### Technologies
In this project was created with this technologies below:
- Kotlin (language)
- Micronaut (Web Framework)
- JAsync (DB Framework)
- Mysql (Database)
- Docker (Container used to run project)
- Intellij IDEA (IDE)
- DataGrip (Database Client)
- Ubuntu 18.04 (OS)

### Running project

The project depends on this applications:

- [Docker](https://docs.docker.com/install/linux/docker-ce/ubuntu/)
- [Docker-compose](https://docs.docker.com/compose/install/)

Docker is used only for  startup the database, so is not necessary install a version of MySQL in your computer and run setup.sql script to create planets table. 

In your terminal inside the project folder, execute docker-compose:
```sh
$ docker-compose up
```
After this command will appears the follow message:
```sh
mysql_1  | Version: '5.7.22'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)
```

And after that execute this command to startup the project in another terminal tab/window:
```sh
$ ./gradlew run
```

Docker-compose completed:
```sh
> Task :run
01:07:24.305 [main] INFO  io.micronaut.runtime.Micronaut - Startup completed in 613ms. Server Running: http://localhost:8080
<===========--> 85% EXECUTING [7s]
> :run
```

As you can see, micronaut is a lightweight framework and the startup is usually less than a minute :D

### Additional information

The API runs at http://localhost:8080

MySQL Database runs at http://localhost:3306

For more configuration see /docker-compose.yml and /src/main/kotlin/application.yml.
