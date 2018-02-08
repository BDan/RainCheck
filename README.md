# RainCheck

RainCheck is a weather information Web service with basic micro-service architecture implemented using Spring Boot. It is intended as an exercise and demonstration.

## Structure

![](https://github.com/BDan/RainCheck/blob/master/raincheck_structure.png "StructureDiagram")


The service is composed of two distinct Spring Boot applications:

* Back End - implements access to external services ([Weather Underground API](https://www.wunderground.com/weather/api/) in this case) and translates data in the internal format of the service (encoded as JSON). It provides a REST API towards other components in the service.
* Front End - acts as web access point, serves API calls to end-user devices and hosts an AngularJS based UI.

## Requirements

* Java 8 SDK
* JAVA_HOME must correctly set
* Windows or Linux OS (Note: application has been developed and tested only on Windows, Linux compatibility is unvalidated).

The application is managed with Maven. A self-installer and runner script (_mvnw_) is included. 

## Installation and configuration

* Download the GIT repository or clone it to the local machine. It contains two Spring Boot application root directories: _frontend_ and _backend_.
* In the folder `backend/config` copy file `aplication.properties.template` to `application.properties`, edit and replace the value of _wu.api.key_ with a valid Weather Underground API key.
* By default front end runs on port 8080 and back end on port 9000. If required change those by editing the value of 'server.port' in file `{frontend/backend}/src/main/resources/application.properties`.
* Back end server's address is configured in front end by values of _weather.server.host_ and _weather.server.port_ `frontend/src/main/resources/application.properties`. By default those are configured to run on _localhost_.

## Running in development mode

In both root directories run `mvnw spring-boot:run` to launch the corresponding component. Access the web interface with a browser at `http://localhost:9000`

## Running from binary package

Self-contained _jar_ packages can be produced by running `mvnw package`. The resulted jar files are created in the subdirectories `APPLICATION_ROOT/target` and can be run by `java -jar JAR_FILE`. The back-end requires the configuration file `config/application.properties` to be present _outside_ of the .jar file.

## Running tests

Run the included test suite with `mvnw test'


