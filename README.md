# Executor

**Executor Dockerfile :**

FROM openjdk:8-jdk-alpine  
EXPOSE 8080  
ADD application.properties application.properties  
ADD executor.jar executor.jar  
ENTRYPOINT ["java","-jar","executor.jar"]

**Link dockerhub** :  docker push denisdugin/executor:latest

Для добавления исполнителя необходимо использовать команду :  

docker run -d -v /home/Save/id_2:/id_2 --env RABBITMQ_HOST=rabbitmq --env RABBITMQ_QUEUE=query-2 --env RABBITMQ_ROUTING_KEY=id_2 --name executor2 --network taxi20_app-tier denisdugin/executor


**Dockerfile for mylti start Executors :**   
FROM openjdk:8-jdk-alpine  
EXPOSE 8080  
ADD application.properties application.properties  
ADD application.properties2 application.properties2  
ADD application.properties3 application.properties3  
ADD executor.jar executor.jar  
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
    container_name: rabbit  
    image: 'denisdugin/myrabbitmq:latest'  
    networks:  
      - app-tier  

  disp:  
     container_name: dispatcher  
     build: ./Dispatcher  
     depends_on:  
        - rabbitmq  
     ports:  
      - "8080:8080"  
     image: dispatcher  
     container_name: dispatcher  
     networks:  
      - app-tier  


  exec:  
     container_name: executor  
     volumes:  
      - ./Save/id_1:/id_1  
     build: ./Executor  
     depends_on:  
        - rabbitmq  
     ports:  
      - "8081:8081"  
     image: executor  
     container_name: executor  
     networks:  
      - app-tier  
	  
	  .  
	  .    
	  .  