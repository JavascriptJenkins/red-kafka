package com.example.demo

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.ProducerListener
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback


@Configuration
@EnableKafka
class KafkaProducerConfig implements ProducerListener  {



    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    void onSuccess(String s, Integer integer, Object o, Object o2, RecordMetadata recordMetadata) {

        System.println("SUCCESS SENDING MESSAGE")

    }

    @Override
    void onError(String s, Integer integer, Object o, Object o2, Exception e) {

        System.println("ERROR SENDING MESSAGE")

    }


    void sendMessage(String msg) {




        kafkaTemplate.send("kafka-broker-test", msg)
        System.println("SENT MESSAGE: "+msg)
    }


    @Override
    boolean isInterestedInSuccess() {
        return false
    }


}
