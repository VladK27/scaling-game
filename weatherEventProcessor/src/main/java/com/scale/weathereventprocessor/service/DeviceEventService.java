package com.scale.weathereventprocessor.service;

import com.scale.weathereventprocessor.dto.DeviceEventDto;

public interface DeviceEventService {
    void processEvent(DeviceEventDto eventDto);
}
