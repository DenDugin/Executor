# Executor

**Executor Dockerfile :**

FROM openjdk:8-jdk-alpine  
EXPOSE 8080  
ADD target/application.properties application.properties  
ADD target/executor-0.0.1-SNAPSHOT.jar executor-0.0.1-SNAPSHOT.jar  
ENTRYPOINT ["java","-jar","executor.jar"]

**Link dockerhub** :  docker push denisdugin/executor:3


**Dockerfile for mylti start Executors :** 
FROM openjdk:8-jdk-alpine  
EXPOSE 8080  
ADD target/application.properties application.properties  
ADD target/application.properties2 application.properties2  
ADD target/application.properties3 application.properties3  
ADD target/executor-0.0.1-SNAPSHOT.jar executor.jar  
ADD start.sh start.sh  
CMD ["sh", "start.sh"]

**Application.properties:** 

server.port=8081

rabbitmq.host=localhost  
rabbitmq.exchange=exchange  
rabbitmq.deadLetterExchange=deadLetterExchange  
rabbitmq.queue=query-1  
rabbitmq.deadLetterQueue=deadLetterQueue  
rabbitmq.routing.key=id_1  
rabbitmq.routing.deadLetter=deadLetter  


spring.rabbitmq.listener.simple.retry.enabled=true  
spring.rabbitmq.listener.simple.retry.initial-interval=1s  
spring.rabbitmq.listener.simple.retry.max-attempts=2  
spring.rabbitmq.listener.simple.retry.max-interval=3s  
spring.rabbitmq.listener.simple.retry.multiplier=2  


# Docker  
rabbitmq.user=guest  
rabbitmq.password=guest  



**docker-compose.yml**

version: '3.3'

services:

networks:
  app-tier:
    driver: bridge

services:

  rabbitmq:
    image: 'bitnami/rabbitmq:latest'
    networks:
      - app-tier
  disp:
     container_name: dispatcher
     build: ./Dispatcher
     ports:
      - "8080:8080"
     image: dispatcher
     networks:
      - app-tier


  java:
     container_name: executor
     volumes:
      - ./Save/id_1:/id_1
     build: ./Executor
     ports:
      - "8081:8081"
     image: executor
     networks:
      - app-tier

  java2:
     container_name: executor2
     volumes:
      - ./Save/id_2:/id_2
     build: ./Executor2
     ports:
      - "8082:8082"
     image: executor2
     networks:
      - app-tier


