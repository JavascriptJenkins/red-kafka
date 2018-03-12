package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@EnableKafka
public class KafkaListenerConfig {

    @KafkaListener(topics = "kafka-broker-test", groupId = "kafka-broker-test")
    public void listen(String message) {

        System.out.println("Received Messasge: " + message);

    }
}
