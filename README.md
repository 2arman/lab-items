# Lab items
 an implementation for a lab items and categories

## Case Description
Scientists need a digital solution to store and identify various items used in their experiments.
These items come in different forms and might include samples, chemicals, devices, etc...

A usual practice in laboratories is to store the items in categories. Each category has attribute
definitions and each item in those categories should fit into those definitions. You can imagine
categories as database tables while attribute definitions are columns. Items are, in this case, the
rows in those database tables.

#### The Task
Please create a REST API application using Java which provides the following functionality:

- Creating categories with attribute definitions
- Creating items in those categories
- Updating items
- Getting items of a category

As described in the background section, you can imagine the above functionality as in visual
database design tools (e.g. MySQL Workbench). You are free to make assumptions and come
up with your own solution. Keep in mind that we do not expect any UI development from you as
this is a pure backend task.   
        
### Design
This implementation used `java 11` , `Maven` , `Spring boot` , `Mockito` , `Junit 5` , `MapStruct` , `JPA`

used `Undertow` as and embedded web server,
, `docker/docker-compose` for easier deployment and running.

also used of `H2` as in-memory database [I used that for simplicity]. (it can be replaced by a relational database like `MySql` well)

for API Documentation and manual testing used the `Swagger` that can easily export and import to `Postman` collections.  
#### Domain
Our main domains are **Item** , **Category** and **Attribute**

#### Api
Api for Project provide for only this type of users: users.

Users can define new Categories and related attribute or update/remove a category in below path.

- `/api/v1/categories`

Users can add item or remove from their lab by using below path:
 
- `/api/v1/items` 
 
For more api documentation details please swagger link (in Build/Run Section).  
In below you can see all project's api:

![API Document](api_doc.png?raw=true "API document") 
 
#### Test
Unit tests was written using mockito and junit.
In this project service layer unit tests has been implemented.
Unit test implemented for main scenario coverage (regarding the time limitation).
All test run in each build or deploy.
#### Improvement issues

Here are some improvements suggestion that 
(if I have time I'll work on that):

- Improving unit test coverage.
- Implementing API and Integration Tests.
- Adding Spring security for adding the Authentication and Authorization.
- Using a consistent Database such as MySQL/Postgres for storing items.
- Adding an Logging Interceptor for logging Request/Response.
- Adding a ControllerAdvice for better exception handling and return responses.

##  Build / Run
This spring boot project can run with both docker or directly compile with maven and run it.
Anyway, you can build and run it with any IDE.

![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) **Docker Compose**
 * **Docker Compose [deploy project]** : ```docker-compose up```
 * **Docker Compose [stop project]** : ```docker-compose down```
 * **Test Via Swagger** http://localhost:8080/swagger-ui/
 
![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) **Docker**
  * **Docker Image Build** : ```docker build -t lab.items .```
  * **Docker Run** : ```docker run -p 8080:8080 lab-items```
  * **Test Via Swagger** http://localhost:8080/swagger-ui/
 
![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) **Maven**     
 * **Build** : ```./mvnw clean package``` | ```mvnw.cmd clean package```
 * **Run** : ```java -jar target\lab-items-0.0.1-SNAPSHOT.jar```
 * **Test Via Swagger** http://localhost:8080/swagger-ui