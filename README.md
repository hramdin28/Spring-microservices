# Spring-microservices
A micro service example architecture with Spring cloud
## Components:
    
### spring-could-config
A spring cloud config server that holds the configurations for the microservice architecture

### discovery
A netflix-eureka discovery service server

### admin
A spring Admin server used to monitor the microservices using actuator endpoints

### producer
A Spring rest web app to send message

### consumer 
A Spring rest web app to send message and receive message from producer service using openfeign clients
