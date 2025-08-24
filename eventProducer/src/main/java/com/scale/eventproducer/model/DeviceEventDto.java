package com.scale.eventproducer.model;

import java.time.LocalDateTime;

public record DeviceEventDto (
        LocalDateTime timestamp,
        int batteryLevel,
        String city,
        String country,
        double longitude,
        double latitude,
        double temperature,
        double windSpeed,
        String weather ) {

}








