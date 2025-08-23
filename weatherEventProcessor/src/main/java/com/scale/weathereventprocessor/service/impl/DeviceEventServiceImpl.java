package com.scale.weathereventprocessor.service.impl;

import com.scale.weathereventprocessor.dto.DeviceEventDto;
import com.scale.weathereventprocessor.model.DeviceEvent;
import com.scale.weathereventprocessor.repository.DeviceEventRepository;
import com.scale.weathereventprocessor.service.DeviceEventService;
import com.scale.weathereventprocessor.util.TimeZoneResolver;
import com.scale.weathereventprocessor.util.UnitConverter;
import com.scale.weathereventprocessor.util.UnitResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
public class DeviceEventServiceImpl implements DeviceEventService {
    private final DeviceEventRepository deviceEventRepository;

    @Autowired
    public DeviceEventServiceImpl(DeviceEventRepository deviceEventRepository) {
        this.deviceEventRepository = deviceEventRepository;
    }

    @Override
    @Transactional
    public void processEvent(DeviceEventDto eventDto) {
        DeviceEvent deviceEvent = resolveEntityFromDto(eventDto);
        this.deviceEventRepository.save(deviceEvent);
    }

    private static DeviceEvent resolveEntityFromDto(DeviceEventDto eventDto) {
        double standardWindSpeed = eventDto.getWindSpeed();
        double standardTemperature = eventDto.getTemperature();

        if (UnitResolver.isUsesImperialSystem(eventDto.getCountry())) {
            standardWindSpeed = UnitConverter.windSpeedToMetric(eventDto.getWindSpeed());
            standardTemperature = UnitConverter.windSpeedToMetric(eventDto.getTemperature());
        }

        ZoneId timeZone = TimeZoneResolver.resolveTimeZone(eventDto.getLatitude(), eventDto.getLongitude());
        OffsetDateTime standardTimestamp = eventDto.getTimestamp().atZone(timeZone).toOffsetDateTime();

        return eventDto.mapToEntity(standardTimestamp, standardTemperature, standardWindSpeed);
    }
}
