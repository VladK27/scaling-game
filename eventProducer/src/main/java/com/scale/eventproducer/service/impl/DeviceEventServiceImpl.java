package com.scale.eventproducer.service.impl;

import com.scale.eventproducer.model.DeviceEventDto;
import com.scale.eventproducer.model.DeviceLocation;
import com.scale.eventproducer.model.Weather;
import com.scale.eventproducer.service.DeviceEventService;
import com.scale.eventproducer.service.LocationService;
import com.scale.eventproducer.util.UnitConverter;
import com.scale.eventproducer.util.UnitResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DeviceEventServiceImpl implements DeviceEventService {
    private static double WIND_SPEED_MAX = 90.0;
    private static double TEMPERATURE_MIN = -40.0;
    private static double TEMPERATURE_MAX = 40.0;

    private final LocationService locationService;

    @Autowired
    public DeviceEventServiceImpl(LocationService locationService) {
        this.locationService = locationService;
    }

    private static LocalDateTime getRandomLocalDateTime() {
        long startEpoch = LocalDateTime.now().minusYears(1).toEpochSecond(java.time.ZoneOffset.UTC);
        long endEpoch = LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC);
        long randomEpoch = ThreadLocalRandom.current().nextLong(startEpoch, endEpoch);

        return LocalDateTime.ofEpochSecond(randomEpoch, 0, java.time.ZoneOffset.UTC);
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public DeviceEventDto getDeviceEvent() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getRandomEventDto());
        }
        return getRandomEventDto();
    }

    private DeviceEventDto getRandomEventDto() {
        LocalDateTime timestamp = getRandomLocalDateTime();
        int batteryLevel = ThreadLocalRandom.current().nextInt(100);
        DeviceLocation deviceLocation = this.locationService.getRandomLocation();
        String city = deviceLocation.city();
        String country = deviceLocation.country();
        double longitude = deviceLocation.longitude();
        double latitude = deviceLocation.latitude();
        double temperature = ThreadLocalRandom.current().nextDouble(TEMPERATURE_MIN, TEMPERATURE_MAX);
        double windSpeed = ThreadLocalRandom.current().nextDouble(WIND_SPEED_MAX);
        String weather = Weather.values()[ThreadLocalRandom.current().nextInt(Weather.values().length)].name();

        if (UnitResolver.isUsesImperialSystem(country)) {
            temperature = UnitConverter.temperatureToFahrenheit(temperature);
            windSpeed = UnitConverter.windSpeedToImperial(windSpeed);
        }

        return new DeviceEventDto(
                timestamp,
                batteryLevel,
                city,
                country,
                longitude,
                latitude,
                temperature,
                windSpeed,
                weather
        );
    }
}
