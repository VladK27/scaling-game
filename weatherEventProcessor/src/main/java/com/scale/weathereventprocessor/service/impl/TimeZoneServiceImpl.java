package com.scale.weathereventprocessor.service.impl;

import com.scale.weathereventprocessor.service.TimeZoneService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.iakovlev.timeshape.TimeZoneEngine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Optional;

@Slf4j
@Service("timeZoneService")
@ConditionalOnProperty(name = "app.mock-time-zone-resolving", havingValue = "false")
public class TimeZoneServiceImpl implements TimeZoneService {
    private TimeZoneEngine engine;

    @PostConstruct
    public void init() {
        log.info("Start initializing time zone engine");
        this.engine = TimeZoneEngine.initialize();
    }

    @Override
    public ZoneId resolveTimeZone(double latitude, double longitude) throws IllegalArgumentException {
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException(
                    String.format("Invalid coordinates: latitude=%f, longitude=%f. " +
                                    "Latitude must be in [-90, 90], longitude must be in [-180, 180]",
                            latitude, longitude));
        }

        Optional<ZoneId> zoneId = engine.query(latitude, longitude);
        if (zoneId.isEmpty()) {
            throw new RuntimeException(String.format(
                    "Time zone not resolved for coordinates: latitude=%f, longitude=%f.",
                    latitude, longitude));
        }

        return zoneId.get();
    }
}