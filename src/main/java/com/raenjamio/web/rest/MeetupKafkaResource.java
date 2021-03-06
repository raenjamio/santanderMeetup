package com.raenjamio.web.rest;

import com.raenjamio.service.MeetupKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetup-kafka")
public class MeetupKafkaResource {

    private final Logger log = LoggerFactory.getLogger(MeetupKafkaResource.class);

    private MeetupKafkaProducer kafkaProducer;

    public MeetupKafkaResource(MeetupKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.send(message);
    }
}
