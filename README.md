# Application information
Jukes is a test application for getting a paginated list of jukeboxes that support a given setting id.
The base framework for the application is Spring Boot 3.  
Spring WebFlux non-blocking framework helps works asynchronously.
## How to see API documentation
Steps:
- Use your IDE to run the app or maven ```mvn spring-boot:run```  command
- Go to the browser and enter the command **http://localhost:8080/swagger-ui.html**
## Run tests
- To run the tests, use the maven command ```mvn test```
## Simple query example
To get a list of jukes, do the following steps
- Request-URI  **/api/jukes?settingId=358a044e-decc-47cc-aaf1-e2253a00998e&model=angelina&offset=6&limit=2**
- HTTP Methods GET
- Host **http://localhost:8080**
## Docker  running steps
- To generate *jukes-0.0.1.jar* file in target directory run  ```mvn clean install```
- To build docker image run ```docker build -t jukes-0.0.1 .  ```
- To run app in docker image run```docker run -p 8080:8080 jukes-0.0.1```
- To check the result run **http://127.0.0.1:8080/api/jukes?settingId=358a044e-decc-47cc-aaf1-e2253a00998e&model=angelina&offset=6&limit=2**