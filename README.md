# MicroServices-Billing

This is a Microservices and Tracing project including three projects:

1. Microservices-Billing system: The main functional projects providing Billing services running as  microservices;

2. Collector service: MySQL database retreiving and translating into JSON format and write out JSON file.

3. d3-frontend: D3 displaying project. 

4. Spring-Cloud-Sleuth-Stream-CustomConsumer: This project is a customized span data consumer which consume span data from rabbitMq. Code resource is from: https://github.com/spring-cloud/spring-cloud-sleuth.git 