package com.scale.eventproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.ArrayList;
import java.util.Random;

@SpringBootApplication
@EnableKafka
public class EventProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventProducerApplication.class, args);
    }

}
