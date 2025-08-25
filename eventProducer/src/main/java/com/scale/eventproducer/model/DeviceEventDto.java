package com.scale.eventproducer.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DeviceEventDto (
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:SS")
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








