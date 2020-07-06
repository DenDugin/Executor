package com.executor.executor.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import javax.annotation.PostConstruct;


@Configuration
public class RabbitConfiguration {

    private Logger logger =  LogManager.getLogger();

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.deadLetterExchange}")
    private String deadLetterExchange;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.deadLetterQueue}")
    private String deadLetterQueue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.deadLetter}")
    private String routingDeadLetter;

    @Value("${rabbitmq.host}")
    private String rabbitHost;

    @Nullable
    @Value("${rabbitmq.user}")
    private String user;

    @Nullable
    @Value("${rabbitmq.password}")
    private String password;



    @PostConstruct
    public void info() {
        logger.info("exchange : " + exchange);
        logger.info("queue : " + queue);
        logger.info("routingKey : " + routingKey);
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(rabbitHost);

        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(deadLetterQueue).build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queue).withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", routingDeadLetter).build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding DLQbinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(routingDeadLetter);
    }


    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }


}
