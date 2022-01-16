package com.kyu.producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MsgController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send/test")
    public String test() {

        kafkaTemplate.send("quickstart", "Test 메시지");

        return "Send Test message";
    }

}
