package com.executor.executor.config;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class RabbitConfiguration {

     Logger logger = Logger.getLogger(RabbitConfiguration.class);


    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.host}")
    private String rabbitHost;

    @Value("${rabbitmq.user}")
    private String user;

    @Value("${rabbitmq.password}")
    private String password;

    // настраиваем соединение с RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(rabbitHost);

//        connectionFactory.setUsername(user);
//        connectionFactory.setPassword(password);

        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    //объявляем очередь
    @Bean
    public Queue queue() {
        return new Queue(queue);
    }


    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }


    @Bean
    public Binding errorBinding1() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);  // привязываем routing key к exchange
    }


    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @PostConstruct
    public void info() {
        logger.info("exchange : " + exchange);
        logger.info("queue : " + queue);
        logger.info("routingKey : " + routingKey);
    }

}
