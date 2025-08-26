package com.scale.weathereventprocessor.kafka;

import com.scale.weathereventprocessor.dto.DeviceEventDto;
import com.scale.weathereventprocessor.service.DeviceEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "device_events")
public class KafkaDeviceEventListener {
    private final DeviceEventService eventService;

    @Autowired
    public KafkaDeviceEventListener(DeviceEventService eventService) {
        this.eventService = eventService;
    }

    @KafkaHandler(isDefault = true)
    public void listenDeviceEvent(DeviceEventDto event) {
        log.debug("Processing event key: {}, timestamp: {}", event.getCity(), event.getTimestamp());
        this.eventService.processEvent(event);
    }
}
