# uK 223
## Requirements
For this project you need the Java JDK version 11 and change the corresponding JDK in the IntelliJ project settings.

To set up the database you need a **PostgreSQL** container running on port 5432 with the command:\
`docker run --name postgres-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres`

The password and the username of the database should be "postgres".
The hibernate username and password can be changed in the application.properties file.


## Setup
Start by opening the project with IntelliJ, then wait until IntelliJ has finished indexing all necessary files and has built the grade project. 
Run the project by clicking on the play button or by clicking on the gradle tab -> demo -> Tasks -> application -> bootRun.

When a similar message is shown in the console the project is up and running:\
`2021-09-14 14:31:15.056 INFO 27988 --- [ main] com.example.demo.DemoApplication : Started DemoApplication in 4.122 seconds (JVM running for 4.991)`

After setting up the project correctly and the container is running, it should be possible to access the endpoint `http://localhost:8080/api/`
You can log in with folllowing infos:\
username: james\
password: bond

When logged in the site should display the text "Hello World" 


##  Common Issues & Fixes
* Restart the PostGreSQL container & check the container is running
* Confirm connection to the DB (e.g. in DBeaver)
* Restart IntelliJ & your Spring Boot application 


##  Hints
* You can add mock data to your database on startup using an SQL script named `data.sql` placed in the resources folder
* You can execute statements at startup by adding them to `AppStartupRunner.run()` 



[Source](https://github.com/LuWidme/uk223) to original GitHub repo.
