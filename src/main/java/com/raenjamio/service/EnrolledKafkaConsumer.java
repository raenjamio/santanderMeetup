package com.raenjamio.service;

import com.raenjamio.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class EnrolledKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(EnrolledKafkaConsumer.class);

    private final AtomicBoolean closed = new AtomicBoolean(false);

    private final KafkaProperties kafkaProperties;

    private KafkaConsumer<String, String> kafkaConsumer;
    private final MeetupService meetupService;

    public EnrolledKafkaConsumer(KafkaProperties kafkaProperties, MeetupService meetupService) {
        this.kafkaProperties = kafkaProperties;
        this.meetupService = meetupService;
    }

    public void start() {
        log.info("Kafka consumer starting...");
        this.kafkaConsumer = new KafkaConsumer<>(kafkaProperties.getConsumerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        consumerThread(MeetupKafkaProducer.TOPIC_UPDATED_NUMBER_PEOPLE);
    }

    private void consumerThread(String topic) {
        Thread consumerThread = new Thread(() -> {
            try {
                kafkaConsumer.subscribe(Collections.singletonList(topic));
                log.info("Kafka consumer started");
                while (!closed.get()) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(3));
                    for (ConsumerRecord<String, String> record : records) {
                        meetupService.incrementAssistant(record.value());
                        log.info("Consumed message in {} : {}", topic, record.value());
                    }
                }
                kafkaConsumer.commitSync();
            } catch (WakeupException e) {
                // Ignore exception if closing
                if (!closed.get()) throw e;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                kafkaConsumer.close();
            }
        });
        consumerThread.start();
    }

    public KafkaConsumer<String, String> getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void shutdown() {
        log.info("Shutdown Kafka consumer");
        closed.set(true);
        kafkaConsumer.wakeup();
    }
}
