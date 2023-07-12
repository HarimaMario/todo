# My-Community Homes TODO list project: Hexagonal Architecture Implementation with SQL & NoSQL Databases

This README file contains information about the benefits of Hexagonal Architecture to implement solutions using both SQL and NoSQL databases, integration of unit tests and Cucumber tests in the Maven test phase for test automation, and steps on starting the database container for MongoDB, MariaDB and the app container.

## Hexagonal Architecture and its advantages
Hexagonal Architecture, also known as Ports and Adapters architecture, is a design pattern that enables an application to be more flexible, maintainable, and scalable. The idea is to isolate the business logic from the outside world (like database, UI, or any external service).

This architecture is advantageous when working with both SQL and NoSQL databases because:

- Easy Database Switch: It provides flexibility to switch between databases (SQL or NoSQL) without altering the core business logic. This is because the dependencies point inwards towards the business logic and are abstracted through interfaces, for this project the spring active profile was used to switch between database.

in the application.yml file, we have the property for the system to choose the database type based on the active profile pass throw environment variables: 
```
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE} # define the active profile, change from non-sql-db to sql-db to change the database type
```

So when we run the application we can pass the active profile throw the environment variables like this example of docker:
```
docker build --build-arg SPRING_PROFILES_ACTIVE=non-sql-db -t my-test .


docker run -d -e SPRING_PROFILES_ACTIVE=non-sql-db -it -p 8080:8080 my-test
```

- Independent Testing: The architecture allows the core business logic to be tested independently from the database implementation. You can provide a mock implementation during testing and swap it with the actual one during production.

- Scalability: By using this architecture, the application can easily scale out to use other databases or even a combination of them, without affecting the core logic of the application.

## Integration of Unit Tests and Cucumber Tests in Maven Test Phase
Maven is a build automation tool that manages project builds, reports, and documentation.

The integration of unit tests and Cucumber tests into the Maven test phase allows for automated testing of the application during the build process. The advantages of this integration include:

- Automation: Integration with Maven ensures that tests are automatically run during the build process. This helps in identifying issues early in the development cycle, leading to quicker resolution and reducing overall development time.

- Consistency: By incorporating tests into the build process, we ensure consistent execution of these tests every time the application is built.

- Regression Testing: Automated testing allows regression tests to be conducted every time changes are made to the code. This ensures that new changes do not break the existing functionality.

Here is how to configure Maven to run Cucumber and Unit tests during the test phase:

```
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.1.2</version>
        <configuration>
            <includes>
            <include>**/*Test.java</include>
            <include>**/*Tests.java</include>
            <include>**/CucumberTestRunner.java</include>
            </includes>
        </configuration>
    </plugin>
```

This way the testing is been executed during the build process, and the build will fail if any test fails. Enabling the Continuous Integration (CI) process to be more efficient and reliable. 

## Spring Security and Basic Authentication
Spring Security is a powerful and highly customizable framework for handling authentication and authorization in Spring-based applications. In this project, we've utilized Spring Security to implement basic authentication for our API endpoints.

Here is a high-level overview of how it works:

- Clients send a request to an endpoint, including an Authorization header with a base64 encoded 'username:password' string.
- Spring Security intercepts the request. The Authorization header is decoded and checked against stored user credentials.
- If the credentials are valid, the request is forwarded to the appropriate controller. If not, the client receives a 401 Unauthorized response.

- To enable basic authentication in Spring Security, we've added in the project configuration class that extends WebSecurityConfigurerAdapter and overrides the configure(HttpSecurity http) method, as shown below:

```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
```
## Transitioning to OAuth with a Third-Party
While basic authentication is simple and easy to implement, it is not the most secure method for larger, more complex applications. In the future, we intend to transition from basic authentication to OAuth2 using a third-party provider like Google, Facebook, or GitHub.

The steps required for this transition are as follows:

- Register the Application: Register the application with your chosen OAuth2 provider to receive a client ID and secret.

- Configure Spring Security: Configure Spring Security to use OAuth2 for authentication. This involves adding the necessary dependencies, updating the security configuration to use OAuth2, and providing the client ID and secret.

- User Consent: The user needs to give consent to share their information with your application.

- Token Exchange: The application exchanges the authorization code for an access token, which is then used to authenticate requests.

- Protect Routes: Use the @PreAuthorize annotation to protect your routes by specifying the required OAuth2 scopes.

- Refresh Tokens: Implement functionality to handle token expiry and renewal.

It's important to note that transitioning to OAuth2 will require additional work, particularly around managing user consent and tokens, but will provide more secure and flexible authentication for your application.

Please refer to the Spring Security documentation and your third-party provider's OAuth2 documentation for more detailed information.

## How to start the database container of MongoDB, MariaDB and the App container
Before you start, ensure that you have Docker installed on your machine.

####  MongoDB:
This the command to run the MongoDB container, with the need configuration to connect to it:
```
docker run -d -p 27017:27017 --name my-mongo  -v todo-list-mongo-data:/data/db -e MONGO_INITDB_ROOT_USERNAME=mongo-user -e MONGO_INITDB_ROOT_PASSWORD=asdf4a65s4dfa65df4 -e MONGO_INITDB_DATABASE=todolist mongo
```

### MariaDB:
This the command to run the MariaDB container, with the need configuration to connect to it:
```
docker run -d -p 3306:3306 --name my-mariadb -v todo-list-mysql-data:/var/lib/mysql --env MARIADB_USER=maria-user --env MARIADB_PASSWORD=hjg56dfSg6wf26h --env MARIADB_ROOT_PASSWORD=32sfd65sdfsd1f -e MARIADB_DATABASE=todolist mariadb
```
### App Container:
First, you need to get the IP address of the host machine. Run the following command:

```
hostname -I | awk '{print $1}'
```

then, you need to build your app's Docker image. Navigate to the directory containing the Dockerfile and run:

## IMPORTANT:
you need to replace the place of holder of the following two commands <HOST-IP> whit the host ip get from the last command this way the app will be able to connect to both databases
```
docker build --build-arg MARIA_HOST=<HOST-IP> --build-arg MONGO_HOST=<HOST-IP> --build-arg SPRING_PROFILES_ACTIVE=non-sql-db -t todo-list-app .
```


Now, run your app container:

```
docker run -d -e MARIA_HOST=<HOST-IP> -e MONGO_HOST=<HOST-IP> -e SPRING_PROFILES_ACTIVE=non-sql-db -it -p 8080:8080 todo-list-app
```

NOTE: The above commands will pull the latest versions of the specified images.
If you need a specific version, replace ```mongo``` or ```mariadb``` with ```mongo:<version>``` or ```mariadb:<version>``` respectively. Replace <version> with the desired version number.


### Test the app
Finally, to test the app you can the following api url:
```
http://<HOST-IP>:8080/api/v1/mycommunity/homes/todolist
```

to connect to the endpoint you need the admin credentials:
```
username: todo-admin-user
```

```
password: as32d1Tfa!2Ssd1f
```

NOTE: The endpoints can be tested using the REST API standard for the CRUD operations
### SWAGGER
The project also has the swagger UI configure to document and test the endpoints, use the same credential from abouve, you can access it by the following url:
``` 
http://<HOST-IP>:8080/api/v1/mycommunity/homes/swagger-ui/index.html
```