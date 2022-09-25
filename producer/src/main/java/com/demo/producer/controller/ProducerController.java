package com.demo.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Autowired
    private ServerProperties serverProperties;

    @RequestMapping("/hello")
    public String home() {
        return "Hello i am the producer on port: " + serverProperties.getPort().toString();
    }
}
