package com.kyu.consumer.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaMessageListener {
    @KafkaListener(topics = "quickstart", groupId = "test")
    public void listen1(String in) {
        System.out.println(in);
    }
}
