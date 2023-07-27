package com.invento.rabbitmqdemo.controller;

import com.invento.rabbitmqdemo.config.MQConfig;
import com.invento.rabbitmqdemo.dto.CustomObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
@RestController
public class MessagePublisher {

    @Autowired
    private RabbitTemplate template;
    // Endpoint to publish a single CustomObject to RabbitMQ
    @PostMapping("/publish")
    public String publishMessage(@RequestBody CustomObject customObject) {
        Random random = new Random();
        int randomId = random.nextInt(1000); // Change the upper limit as needed

        customObject.setMessageId(randomId);
        customObject.generateRandomMessage();
        customObject.setMessageDate(new Date());

        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, customObject);
        return "Message Published";
    }
    // Endpoint to publish multiple CustomObjects to RabbitMQ in parallel
    @PostMapping("/publishMultiple")
    public Flux<String> publishMultipleMessages(@RequestBody List<CustomObject> customObjects) {
        return Flux.fromIterable(customObjects)
                .flatMap(this::generateRandomMessageAndPublish)
                .doOnNext(response -> System.out.println("Response: " + response));
    }

    // Helper method to generate a random message, set it in the CustomObject, and publish to RabbitMQ
    private Mono<String> generateRandomMessageAndPublish(CustomObject customObject) {
        Random random = new Random();
        int randomId = random.nextInt(1000); // Change the upper limit as needed

        customObject.setMessageId(randomId);
        customObject.generateRandomMessage();
        customObject.setMessageDate(new Date());

        return Mono.fromRunnable(() -> template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, customObject))
                .then(Mono.just("Message Published for ID: " + randomId))
                .subscribeOn(Schedulers.elastic());
    }
}


