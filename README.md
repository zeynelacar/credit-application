# Credit Application System

Java Spring project for a credit application.

## Responsibilities
This project contains the code of a credit application system. A applicant can register to system then make an application. 
Their application result will be returned to them immediately. In this application applicants can be deleted,added,updated,and can be listed.Viewing the applicants is privileged to
authorized applicants however. Applications can be made by authenticated users, and they can search their results later by questioning with their national identity number. 
User signup and signin services open to anyone.<br> 
 


## Technical Features
- Project has a monolithic architecture.
- Developed with Java Spring.
- PostgreSQL for database.
- JPA/Hibernate for ORM.
- JUnit for testing.
- Java Spring, JWT for security.
- Docker for containerization.
- CircleCI for automated CI processes.
- OpenAPI for documentation.


### Database

For database, project uses PostgreSQL, it uses the port at 5342. Contains three 6 tables, 3 of them for core project, and other three for security implementations.
Tables does not have connections with eachother because they are considered as like they are totally separate entities that are just get in touch with eachother in some service scenerio.
JPA/Hibernate is used for Object Relational Mapping with the database.

### Architecture & Development Process

Project has a monolithic architecture. It uses Java Spring, Spring Boot. While developing the SOLID principles, and clean coding principles are considered important. 
Also developed coniderate of RESTful conventions.<br>
**Some important notes to add:**<br>
- In order to make workflow smooth, when there isn't any score available for the given applicant or the national identity number, score value randomly generated inside the service and then saved.
- Results are not added to the system. They can only be generated by result service,thus application controller.

### Security

Spring Security used for security, JWT is the underlying technology in authentication and authorization. 
Under the config file of the project there can be seen there are two security configurations. They create profile so you can choose the secure profile or unsecure profile.

### Testing

Core part of the application which consist of score,applicant,result services as well as applicant and application controllers are tested. Tests are written using JUnit. 
User service did not tested because of the use of various specialities of Spring Security makes it quite complex to handle in a test case. 
However their standard and effects are tested locally with use of Postman by manually checking the endpoints.


### CI/CD

Docker used for containerization, project containerized however, database did not. So it may cause problems of integration. 
Docker-compose has been considered however not been implemented. CircleCI config file created. Its been rough however it works yet because of the problem previously mentioned.
It runs into integration error.

### Documentation

Project uses openAPI for documentation.
In order to access documentation assuming that you didnot change the port settings of the application, 
you should type http://localhost:8080/swagger-ui.html in your browser. **In order to documentation not run into any problem, running application on unsecure profile is recommended.**

