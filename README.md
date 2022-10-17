

# Microservice Skeleton Designed By Ram C.


This project is a simple skeleton code for microservice architecture pattern using Spring Cloud, Spring Boot, Spring Security.


## Services

By now, the functional services are still decomposed into two core services. Each of them can be tested, built, and deployed independently.

![Infrastructure plan](http://res.cloudinary.com/imrenagi-com/image/upload/v1494871114/Untitled_cwhlwy.png)


### PRODUCT SERVICE
Contains API related to creating and retrieving all product information. In this service, we are also demonstrating how we use 
user's privilege in accessing the API. For example, the access to `GET ` `/products` endpoint will only be allowed for user whose `READ_BASIC_INFORMATION` privilege, but the access  
 to other endpoints don't require any special privilege as long as it has correct scope. Please refer to spring security docs [here](http://projects.spring.io/spring-security-oauth/docs/oauth2.html) for more details.

| Method    | Path                             | Description                    | Scope |  Privilege |
|-----------|----------------------------------|--------------------------------|-------|------------|
| GET       | /product/getAllProduct           | To get all products            | ui    | ALL_ACCESS |
| POST      | /product/saveProduct             | To save product                | ui    | ALL_ACCESS |
| GET       | /product/getProductById/{id}     | To Get particular product      | ui    | ALL_ACCESS |
| PUT       | /product/updateProduct           | To update product              | ui    | ALL_ACCESS |
| DELETE    | /product/deleteProduct/{id}      | To delete product bt id        | ui    | ALL_ACCESS |
| GET       | /product/getProductByName/pencile| To get product  by name        | ui    | ALL_ACCESS |
| POST      | /product/uploadSheet             | To upload product from excel   | ui    | ALL_ACCESS |
| GET       | /product/getProductByName/pencile| To export product from DB      | ui    | ALL_ACCESS |

and many more....


### SUPPLIER SERVICE

| Method    | Path                             | Description                    | Scope |  Privilege |
|-----------|----------------------------------|--------------------------------|-------|------------|
| POST      | /supplier/saveSupplier           | To save supplier             | ui    | ALL_ACCESS |
| GET       | /supplier/getSupplierById/1001   | To Get particular product    | ui    | ALL_ACCESS |


### Notes
* Each microservice has it's own database and there is no way to access the database directly from other services.
* The services in this project are using MySQL for the persistent storage. In other case, it is also possible for one service 
to use any type of database (SQL or NoSQL).
* Service-to-service communiation is done by using REST API. It is pretty convenient to use HTTP call in Spring
since it provides a simplify HTTP layer service called RestTemplate (discussed later). For the next iteration, I'm also planning to use
 asyncronous message transfer using Apache Kafka since Kafka will allow us to send message to several service easily.

### Infrastructure
Spring Cloud is a really good web framework that we can use for building a microservice infrastructure since it provides 
broad supporting tools such as Load Balancer, Service registry, Monitoring, and Configuration.

![This image is taken from PiggyMetrics](https://cloud.githubusercontent.com/assets/6069066/13906840/365c0d94-eefa-11e5-90ad-9d74804ca412.png)
Image source: [PiggyMetrics](https://github.com/sqshq/PiggyMetrics)

### Eureka Server
Eureka Server is an application that holds the information about all client-service applications. Every Micro service will register into the Eureka server and Eureka server knows all the client applications running on each port and IP address. Eureka Server is also known as Discovery Server.

![image](https://user-images.githubusercontent.com/61204888/196253639-c291ff9c-58d6-49f2-a25f-8fd84a83cd64.png)


### Config *
[Spring Cloud Config](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html) is horizontally scalable centralized configuration service for distributed systems. It uses a pluggable repository layer that currently supports local storage, Git, and Subversion. 

For the purpose of proof-of-concept, we just use spring `native profile`, which simply loads config file from github repository. You can see that we have `shared` repo [in [Config service resource](Config File)](https://github.com/ram-chadar/ms-config/blob/main/application.yml). When a service (e.g. product-service) wants to request it's configuration, the config service will response with `application.yml` (which is shared among all services).
    
##### Client side usage
Just build Spring Boot application with `spring-cloud-starter-config` dependency, autoconfiguration will do the rest.

Now you don't need any embedded properties in your application. Just provide `bootstrap.yml` with application name and Config service url:
```yml
spring:
  cloud:
    config:
      enabled: true
      uri: http://localhost:9190
```


### Database

I use MySQL for persistent data storage for several services in this application. To help me in doing some database schema migration, I use [Flyway by boxfuse](https://flywaydb.org/) to help me creating all tables and updating the schema. 

To use flyway in Spring application, simply define this configuration in service config file and put all migration scripts in `db/migration` under resources folder.
```yml
flyway:
  url: jdbc:mysql://service-auth-db:3306/auth
  user: ${SERVICE_AUTH_DB_USER}
  password: ${SERVICE_AUTH_DB_PASSWORD}
  locations: classpath:db/migration
  enabled: true
```
Using flyway in this project is really simple because we don't have to explicitly run migration command. Spring will handle this process for us.
 
### API Gateway *

![image](https://user-images.githubusercontent.com/61204888/196254879-2869294c-b6ac-40db-829f-86d93875ca36.png)


As you can see, there are three core services, which expose external API to client. In a real-world systems, this number can grow very quickly as well as whole system complexity. Actualy, hundreds of services might be involved in rendering one complex webpage. 

In theory, a client could make requests to each of the microservices directly. But obviously, there are challenges and limitations with this option, like necessity to know all endpoints addresses, perform http request for each peace of information separately, merge the result on a client side. Another problem is non web-friendly protocols, which might be used on the backend.

Usually a much better approach is to use API Gateway. It is a single entry point into the system, used to handle requests by routing them to the appropriate backend service or by invoking multiple backend services and [aggregating the results](http://techblog.netflix.com/2013/01/optimizing-netflix-api.html). Also, it can be used for authentication, insights, stress and canary testing, service migration, static response handling, active traffic management.

Netflix opensourced [such an edge service](http://techblog.netflix.com/2013/06/announcing-zuul-edge-service-in-cloud.html), and now with Spring Cloud we can enable it with one `@EnableZuulProxy` annotation. In this project, I use Zuul to store static content (ui application) and to route requests to appropriate microservices. Here's a simple prefix-based routing configuration for Notification service:

```yml

spring:
  application:
    name: API-GATEWAY
  cloud:
    gateway:
      routes:
      - id: PRODUCT-SERVICE
        uri: lb://PRODUCT-SERVICE
        predicates:
          - Path=/product/**
         
      - id: SUPPLIER-SERVICE
        uri: lb://SUPPLIER-SERVICE
        predicates:
          - Path=/supplier/**
      
 

server:
  port: 9191 

```

That means all requests starting with `/product` will be routed to PRODUCT-SERVICE and `/supplier` will be forwarded to SUPPLIER-SERVICE. There is no hardcoded address, as you can see. API gateway uses Service discovery mechanism to locate PRODUCT-SERVICE instances and also SUPPLIER-SERVICE.

### Service Discovery *
Another commonly known architecture pattern is Service discovery. It allows automatic detection of network locations for service instances, which could have dynamically assigned addresses because of auto-scaling, failures and upgrades.

The key part of Service discovery is Registry. I use Netflix Eureka in this project. Eureka is a good example of the client-side discovery pattern, when client is responsible for determining locations of available service instances (using Registry server) and load balancing requests across them.

With Spring Boot, you can easily build Eureka Registry with `spring-cloud-starter-eureka-server` dependency, `@EnableEurekaServer` annotation and simple configuration properties.

Client support enabled with `@EnableDiscoveryClient` annotation an `bootstrap.yml` with application name:
``` yml
spring:
  application:
    name: XXXXX-XXXXX
```

Now, on application startup, it will register with Eureka Server and provide meta-data, such as host and port, health indicator URL, home page etc. Eureka receives heartbeat messages from each instance belonging to a service. If the heartbeat fails over a configurable timetable, the instance will be removed from the registry.

Also, Eureka provides a simple interface, where you can track running services and number of available instances: `http://localhost:8761`

## How to run all things

<ul>
First run eureka server >> port 8761
Second run  Config Server >> port 9190
Third run API gateway >> port  9191
Fourth run Product service  >> port 8080
Fifth run Supplier service  >> port 8081 
 
</ul>


### Important Endpoint *
* [http://localhost:9191](http://localhost:80) - Gateway
* [http://localhost:8761](http://localhost:8761) - Eureka Dashboard


### Below points not a part of our projects its just for knokledge perpose

### Load balancer, Circuit Breaker and Http Client *

#### Ribbon
Ribbon is a client side load balancer which gives you a lot of control over the behaviour of HTTP and TCP clients. Compared to a traditional load balancer, there is no need in additional hop for every over-the-wire invocation - you can contact desired service directly.

Out of the box, it natively integrates with Spring Cloud and Service Discovery. Eureka Client provides a dynamic list of available servers so Ribbon could balance between them.

#### Hystrix
Hystrix is the implementation of [Circuit Breaker pattern](http://martinfowler.com/bliki/CircuitBreaker.html), which gives a control over latency and failure from dependencies accessed over the network. The main idea is to stop cascading failures in a distributed environment with a large number of microservices. That helps to fail fast and recover as soon as possible - important aspects of fault-tolerant systems that self-heal.

Besides circuit breaker control, with Hystrix you can add a fallback method that will be called to obtain a default value in case the main command fails.

Moreover, Hystrix generates metrics on execution outcomes and latency for each command, that we can use to monitor system behavior.

#### Feign
Feign is a declarative Http client, which seamlessly integrates with Ribbon and Hystrix. Actually, with one `spring-cloud-starter-feign` dependency and `@EnableFeignClients` annotation you have a full set of Load balancer, Circuit breaker and Http client with sensible ready-to-go default configuration.

Here is an example from Account Service:

``` java
@FeignClient(name = "service-auth")
public interface AuthServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/uaa/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void createUser(User user);

}
```

- Everything you need is just an interface
- You can share `@RequestMapping` part between Spring MVC controller and Feign methods
- Above example specifies just desired service id - `service-auth`, thanks to autodiscovery through Eureka (but obviously you can access any resource with a specific url)

### Monitoring Dashboard *

In this project configuration, each microservice with Hystrix on board pushes metrics to Turbine via Spring Cloud Bus (with AMQP broker). The Monitoring project is just a small Spring boot application with [Turbine](https://github.com/Netflix/Turbine) and [Hystrix Dashboard](https://github.com/Netflix/Hystrix/tree/master/hystrix-dashboard).

See below [how to get it up and running](https://github.com/imrenagi/microservice-skeleton/tree/master/readme/README.md#how-to-run-all-things).

Let's see our system behavior under load: A service calls another service and it responses with a vary imitation delay. Response timeout threshold is set to 1 second.

<img width="880" src="https://cloud.githubusercontent.com/assets/6069066/14194375/d9a2dd80-f7be-11e5-8bcc-9a2fce753cfe.png">

<img width="212" src="https://cloud.githubusercontent.com/assets/6069066/14127349/21e90026-f628-11e5-83f1-60108cb33490.gif">	| <img width="212" src="https://cloud.githubusercontent.com/assets/6069066/14127348/21e6ed40-f628-11e5-9fa4-ed527bf35129.gif"> | <img width="212" src="https://cloud.githubusercontent.com/assets/6069066/14127346/21b9aaa6-f628-11e5-9bba-aaccab60fd69.gif"> | <img width="212" src="https://cloud.githubusercontent.com/assets/6069066/14127350/21eafe1c-f628-11e5-8ccd-a6b6873c046a.gif">
--- |--- |--- |--- |
| `0 ms delay` | `500 ms delay` | `800 ms delay` | `1100 ms delay`
| Well behaving system. The throughput is about 22 requests/second. Small number of active threads in Statistics service. The median service time is about 50 ms. | The number of active threads is growing. We can see purple number of thread-pool rejections and therefore about 30-40% of errors, but circuit is still closed. | Half-open state: the ratio of failed commands is more than 50%, the circuit breaker kicks in. After sleep window amount of time, the next request is let through. | 100 percent of the requests fail. The circuit is now permanently open. Retry after sleep time won't close circuit again, because the single request is too slow.






