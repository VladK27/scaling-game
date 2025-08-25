package com.scale.weathereventprocessor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scale.weathereventprocessor.model.DeviceEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * DTO for {@link com.scale.weathereventprocessor.model.DeviceEvent}
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEventDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:SS")
    private LocalDateTime timestamp;
    private int batteryLevel;
    private String city;
    private String country;
    private double longitude;
    private double latitude;
    private float temperature;
    private float windSpeed;
    private String weather;


    /**
     * Maps a DTO object to a {@link DeviceEvent} entity.
     * <p>
     * All fields from the DTO are mapped into the entity
     * except for {@code timestamp}, {@code temperature},
     * and {@code windSpeed}, since these values depend on
     * regional data and are explicitly provided as method parameters.
     * </p>
     *
     * @param standardTimestamp standardized timestamp value to be set instead of DTO's original timestamp
     * @param standardTemperature standardized temperature value to be set instead of DTO's original temperature
     * @param standardWindSpeed standardized wind speed value to be set instead of DTO's original wind speed
     * @return a new {@link DeviceEvent} entity with fields populated from the DTO and overridden values
     */
    public DeviceEvent mapToEntity(OffsetDateTime standardTimestamp,
                                   double standardTemperature,
                                   double standardWindSpeed) {
        DeviceEvent deviceEvent = new DeviceEvent();
        deviceEvent.setCity(this.city);
        deviceEvent.setCountry(this.country);
        deviceEvent.setWeather(this.weather);
        deviceEvent.setBatteryLevel(this.batteryLevel);
        deviceEvent.setTimestamp(standardTimestamp);
        deviceEvent.setTemperature(standardTemperature);
        deviceEvent.setWindSpeed(standardWindSpeed);

        return deviceEvent;
    }
}