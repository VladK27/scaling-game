package com.scale.eventproducer.repository;

import com.scale.eventproducer.model.DeviceLocation;

import java.util.List;

public interface LocationRepository {
    List<DeviceLocation> loadLocations();
}
