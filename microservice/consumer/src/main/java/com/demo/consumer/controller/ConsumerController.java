package com.demo.consumer.controller;

import com.demo.consumer.client.ProducerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    private final ProducerClient producerClient;

    public ConsumerController(ProducerClient producerClient) {
        this.producerClient = producerClient;
    }

    @RequestMapping("/hello")
    public String home() {
        return "Hello i am the consumer!";
    }

    @RequestMapping("/helloProducer")
    public String homeProducer() {
        return producerClient.home();
    }
}
