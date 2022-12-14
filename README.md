# cafeteria-event
cafeteria event home task<br/>

RESTful Spring boot application<br/>
Simple ui for selling products / products are displayed with out pagination<br/>
REST services for updating and commiting transactions<br/>
Spring security layer with basic auth scheme / {noop} passwords as plain text in the database - 
spring supports encryption that needs to be specified (eg. {bcrypt}passwrord crypted string)
PostgreSQL 15 database<br/>
Swagger-ui for testing and documenting the REST services<br/>
gradle build system<br/>

# Running the application<br/>
./gradlew bootRun from the sources location<br/>
java -jar cafeteria-event-0.0.1-SNAPSHOT.jar<br/>

#Application URL's <br/>
http://localhost:8080 - application<br/>
http://localhost:8080/swagger/index.html - swagger REST endpoints documentation<br/>

