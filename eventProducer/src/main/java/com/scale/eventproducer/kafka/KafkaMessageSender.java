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

@Slf4j
@Component
public class KafkaMessageSender {
    private final KafkaTemplate<String, DeviceEventDto> kafka;
    private final DeviceEventService eventService;
    private final String topicName;

    private final static int MESSAGES_TO_SEND = 100;

    @Autowired
    public KafkaMessageSender(KafkaTemplate<String, DeviceEventDto> kafka, DeviceEventService eventService,
                              @Value("${spring.kafka.template.default-topic}") String topicName) {
        this.topicName = topicName;
        this.kafka = kafka;
        this.eventService = eventService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMessages() {
        log.info("Start initializing {} events to send in kafka", MESSAGES_TO_SEND);
        long before = System.currentTimeMillis();
        List<DeviceEventDto> events = initEventList();
        log.info("Successfully initialized {} events in {} ms", MESSAGES_TO_SEND, System.currentTimeMillis() - before);

        log.info("Start sending {} events in kafka", MESSAGES_TO_SEND);
        before = System.currentTimeMillis();
        events.forEach(event -> kafka.send(topicName, event.city(), event));
        log.info("Successfully sent {} events in {} ms", MESSAGES_TO_SEND, System.currentTimeMillis() - before);
    }

    private List<DeviceEventDto> initEventList() {
        List<DeviceEventDto> events = new ArrayList<>(MESSAGES_TO_SEND);
        for (int i = 0; i < MESSAGES_TO_SEND; i++) {
            events.add(eventService.getDeviceEvent());
        }

        return events;
    }
}
