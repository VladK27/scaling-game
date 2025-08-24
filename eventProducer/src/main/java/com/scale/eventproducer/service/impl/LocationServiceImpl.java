package com.scale.eventproducer.service.impl;

import com.scale.eventproducer.model.DeviceLocation;
import com.scale.eventproducer.repository.LocationRepository;
import com.scale.eventproducer.service.LocationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    private final Random random = new Random();
    private List<DeviceLocation> locations;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @PostConstruct
    public void loadLocations() {
        this.locations = locationRepository.loadLocations();
    }

    @Override
    public DeviceLocation getRandomLocation() {
        return locations.get(random.nextInt(0, locations.size()));
    }
}
