package com.scale.weathereventprocessor.service;

import java.time.ZoneId;

public interface TimeZoneService {
    /**
     * Resolves the {@link java.time.ZoneId} (time zone) for a given geographic location.
     * <p>
     * This method determines the IANA time zone identifier based on the provided
     * latitude and longitude coordinates. The result can then be used for
     * time zone–aware operations, such as converting timestamps to local time.
     * </p>
     *
     * @param latitude  the latitude of the location in decimal degrees
     *                  (positive for north, negative for south)
     * @param longitude the longitude of the location in decimal degrees
     *                  (positive for east, negative for west)
     * @return the corresponding {@link ZoneId} for the location
     * @throws IllegalArgumentException if the provided coordinates are invalid
     *                                  or cannot be resolved to a time zone
     */
    ZoneId resolveTimeZone(double latitude, double longitude) throws IllegalArgumentException;
}
