package com.invento.rabbitmqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    // Constants representing the names of the queue, exchange, and routing key
    public static final String QUEUE = "message_queue";
    public static final String EXCHANGE = "message_exchange";
    public static final String ROUTING_KEY = "message_routingKey";

    // Bean definition for the RabbitMQ Queue
    @Bean
    public Queue queue() {
        // Create a new Queue instance with the name specified in the QUEUE constant
        return new Queue(QUEUE);
    }

    // Bean definition for the RabbitMQ Topic Exchange
    @Bean
    public TopicExchange exchange() {
        // Create a new TopicExchange instance with the exchange name "EXCHANGE"
        return new TopicExchange("EXCHANGE");
    }

    // Bean definition for the Binding between the Queue and Topic Exchange
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        // Create a new Binding instance that binds the queue to the exchange
        // using the routing key specified in the ROUTING_KEY constant
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    // Bean definition for the MessageConverter
    @Bean
    public MessageConverter messageConverter() {
        // Create a new Jackson2JsonMessageConverter instance
        // This converter is used to convert Java objects to JSON format and vice versa
        return new Jackson2JsonMessageConverter();
    }

    // Bean definition for the RabbitTemplate
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        // Create a new RabbitTemplate instance using the provided connectionFactory
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // Set the MessageConverter to be used by the RabbitTemplate
        template.setMessageConverter(messageConverter());
        // Return the configured RabbitTemplate
        return template;
    }
}