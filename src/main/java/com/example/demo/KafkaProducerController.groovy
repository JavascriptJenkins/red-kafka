package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/KafkaProducer/")
class KafkaProducerController {



    @Autowired
    KafkaProducerConfig kafkaProducerConfig



    @RequestMapping("sendMessage")
    boolean sendMessage(){

        String msg = "this is a kafka message"

        System.out.println("kafkaWorker.sendMessage(): ");

        return kafkaProducerConfig.sendMessage(msg)
    }














}