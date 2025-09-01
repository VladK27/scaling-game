package com.scale.eventproducer.kafka;

import com.scale.eventproducer.model.DeviceEventDto;
import com.scale.eventproducer.service.DeviceEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class KafkaMessageSender {
    private final KafkaTemplate<String, DeviceEventDto> kafka;
    private final DeviceEventService eventService;
    private final String topicName;

    private final static int MESSAGES_TO_SEND = 1000; // MUST be a multiple of the number of threads
    private final static int THREADS_COUNT = 10;

    @Autowired
    public KafkaMessageSender(KafkaTemplate<String, DeviceEventDto> kafka, DeviceEventService eventService,
                              @Value("${spring.kafka.template.default-topic}") String topicName) {
        this.topicName = topicName;
        this.kafka = kafka;
        this.eventService = eventService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMessages() throws InterruptedException {
        log.info("WARMUP");
        List<DeviceEventDto> warmupEvents = initEventList();
        sendInSingleThread(warmupEvents);

        log.info("SINGLE THREAD");
        //log.info("Start initializing {} events to send in kafka", MESSAGES_TO_SEND);
        long before = System.currentTimeMillis();
        List<DeviceEventDto> events = initEventList();
        //log.info("Successfully initialized {} events in {} ms", MESSAGES_TO_SEND, System.currentTimeMillis() - before);
        before = System.currentTimeMillis();
        sendInSingleThread(events);
        log.info("Successfully sent {} events in {} ms", MESSAGES_TO_SEND, System.currentTimeMillis() - before);

        log.info("PARALLEL STREAM");
        //log.info("Start initializing {} events to send in kafka", MESSAGES_TO_SEND);
        before = System.currentTimeMillis();
        events = initEventList();
        //log.info("Successfully initialized {} events in {} ms", MESSAGES_TO_SEND, System.currentTimeMillis() - before);
        before = System.currentTimeMillis();
        sendUsingParallelStream(events);
        log.info("Successfully sent {} events in {} ms", MESSAGES_TO_SEND, System.currentTimeMillis() - before);

        log.info("THREAD POOL");
        //log.info("Start initializing {} events to send in kafka", MESSAGES_TO_SEND);
        System.currentTimeMillis();
        events = initEventList();
        //log.info("Successfully initialized {} events in {} ms", MESSAGES_TO_SEND, System.currentTimeMillis() - before);
        before = System.currentTimeMillis();
        sendUsingThreadPool(events);
        log.info("Successfully sent {} events in {} ms", MESSAGES_TO_SEND, System.currentTimeMillis() - before);
    }

    private void sendUsingParallelStream(List<DeviceEventDto> events) {
        events.parallelStream().forEach(event -> kafka.send(topicName, event.city(), event));
    }

    private void sendUsingThreadPool(List<DeviceEventDto> events) throws InterruptedException {
        try (ExecutorService pool = Executors.newCachedThreadPool()) {
            log.info("Start sending {} events in kafka", MESSAGES_TO_SEND);
            List<List<DeviceEventDto>> chunks = getChunks(events, THREADS_COUNT);

            chunks.forEach(ch -> {
                pool.submit(() -> {
                    for (DeviceEventDto event : ch) {
                        kafka.send(topicName, event.city(), event);
                    }
                });
            });

            pool.shutdown();
            pool.awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    private void sendInSingleThread(List<DeviceEventDto> events) {
        events.forEach(event -> kafka.send(topicName, event.city(), event));
    }

    private List<List<DeviceEventDto>> getChunks(List<DeviceEventDto> list, int numberOfChunks) {
        List<List<DeviceEventDto>> chunks = new ArrayList<>();
        for (int i = 0; i < numberOfChunks; i++) {
            int from = list.size() / numberOfChunks * i;
            int to = list.size() / numberOfChunks * (i + 1);

            chunks.add(list.subList(from, to));
        }

        return chunks;
    }

    private List<DeviceEventDto> initEventList() {
        List<DeviceEventDto> events = new ArrayList<>(MESSAGES_TO_SEND);
        for (int i = 0; i < MESSAGES_TO_SEND; i++) {
            events.add(eventService.getDeviceEvent());
        }

        return events;
    }
}