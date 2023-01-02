# cafeteria-event
Cafeteria Event home task<br/>

RESTful Spring boot application<br/>
- Simple ui for selling products / products are displayed with out pagination<br/>
- REST services for updating and commiting transactions<br/>
- Spring security layer with basic auth scheme / {noop} passwords as plain text in the database(for testing) - 
spring supports encryption that needs to be specified (eg. {bcrypt}password encrypted string)
- PostgreSQL 15 database<br/>
- Swagger-ui for testing and documenting the REST services<br/>
- Docker integration
- Gradle build system 7.6
- jdk 19 (source level is 17, as docker image is created with java version 17- despite the Dockerfile's java version)

# Running the application<br/>
Before running the application it is needed to supply the database.host variable in application.properties file <br/>
(localhost if running from local machine- postgre database needs to be installed outside docker container) <br/>
Application comes with docker default localhost configuration(windows client) (database.host=172.17.0.2-  [why IP is used in docker conf](#Running-in-docker-container)<br/>
- ./gradlew bootRun from the sources location<br/>
- java -jar cafeteria-event-0.0.1-SNAPSHOT.jar<br/>
- ## two web application users
- user_one, pwd_one
- user_two, pwd_two
# Application URL's <br/>
http://localhost:8080 - application<br/>
http://localhost:8080/swagger/index.html - swagger REST endpoints documentation<br/>

# Running in docker container
While in my Windows environment the docker default way of setting the containers on the same network with network type=bridge and auto-exposing
the ports did not work then I am using the default IP(172.17.0.2) and exposing the database port for database service and application 
through IntelliJ IDEA community edition's Docker plugin's "Port Bindings" view.
*(if adding -p 5432:5432 to the docker executable command then port bindings take effect as specified)*
##Prerequisites
- Local Windows Docker client needs to be installed
- Application and database service need to be deployed from separate projects (separate compose.yaml files - ports exposing issue, check the image docker-port-binding-image.png)
- IntelliJ IDEA community edition (optional for docker port exposing)
### Expose database and application ports
IntelliJ IDEA Docker plugin's "Port Bindings" view can be used to expose the ports in docker container (local docker client)

#Known issues
- Swagger-ui gradle/maven plugin does not support Spring annotations (using, GET, PATH, etc. from javax.ws.rs-api trigger some config generation)
- Docker automatic host ports exposing and dns configs to enable inter container communication between images do not work

