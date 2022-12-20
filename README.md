# cafeteria-event
cafeteria event home task<br/>

RESTful Spring boot application<br/>
- Simple ui for selling products / products are displayed with out pagination<br/>
- REST services for updating and commiting transactions<br/>
- Spring security layer with basic auth scheme / {noop} passwords as plain text in the database - 
spring supports encryption that needs to be specified (eg. {bcrypt}passwrord crypted string)
- PostgreSQL 15 database<br/>
- Swagger-ui for testing and documenting the REST services<br/>
- Gradle build system 7.5.1 <br/>
- jdk 17

# Running the application<br/>
Before running the application it is needed to supply the database.host variable in application.properties file <br/>
(localhost if running from local machine- postgre database needs to be installed outside docker container) <br/>
Application comes with docker default localhost configuration(windows client) (database.host=172.17.0.2-  [why IP is used in docker conf](#Running-in-docker-container)<br/>
- ./gradlew bootRun from the sources location<br/>
- java -jar cafeteria-event-0.0.1-SNAPSHOT.jar<br/>

# Application URL's <br/>
http://localhost:8080 - application<br/>
http://localhost:8080/swagger/index.html - swagger REST endpoints documentation<br/>

# Running in docker container
While in my windows environment the docker default way of setting the containers on the same network with type=bridge and auto-exposing
the ports did not work then I am using the default IP(172.17.0.2) and exposing the database port for database service and application 
through IntelliJ IDEA community edition Docker plugins "Port Bindings" view.
*(docker client exposes ports with -P "Randomly publish all exposed ports" but system defaults for outside connections are added
rather than the ports specified with binding directive -p)*
##Prerequisites
- Local windows docker client needs to be installed
- Application and database service need to be deployed from separate projects (separate compose.yaml files - ports exposing issue, check the image docker-port-binding-image.png)
### Expose database and application ports
IntelliJ IDEA Docker plugin's "Port Bindings" view can be used to expose the ports in docker container (local docker client)

#Known issues
- Swagger-ui gradle/maven plugin does not support Spring annotations (using, GET, PATH, etc. from javax.ws.rs-api trigger some config generation)
- Docker automatic host ports exposing and dns configs to enable inter container communication do not work

