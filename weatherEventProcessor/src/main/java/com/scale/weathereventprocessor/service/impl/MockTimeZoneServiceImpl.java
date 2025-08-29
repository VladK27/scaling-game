package com.scale.weathereventprocessor.service.impl;

import com.scale.weathereventprocessor.service.TimeZoneService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service("mockTimeZoneService")
@ConditionalOnProperty(name = "app.mock-time-zone-resolving", havingValue = "true")
public class MockTimeZoneServiceImpl implements TimeZoneService {
    private static final ZoneId mockZoneId = ZoneId.of("UTC-3");

    @Override
    public ZoneId resolveTimeZone(double latitude, double longitude) throws IllegalArgumentException {
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException(
                    String.format("Invalid coordinates: latitude=%f, longitude=%f. " +
                                    "Latitude must be in [-90, 90], longitude must be in [-180, 180]",
                            latitude, longitude));
        }

        return mockZoneId;
    }
}
