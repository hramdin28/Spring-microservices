package com.demo.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("${clients.producer.name}")
public interface ProducerClient {
    @RequestMapping("/hello")
    String home();
}
