package com.scale.eventproducer.model;

public record DeviceLocation (
        String city,
        String country,
        double longitude,
        double latitude ) {
}
