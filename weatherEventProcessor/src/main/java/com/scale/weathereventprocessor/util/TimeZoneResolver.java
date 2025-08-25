package com.scale.weathereventprocessor.util;

import net.iakovlev.timeshape.TimeZoneEngine;

import java.time.ZoneId;
import java.util.Optional;

public class TimeZoneResolver {
    private static final TimeZoneEngine engine = TimeZoneEngine.initialize();

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
    public static ZoneId resolveTimeZone(double latitude, double longitude) throws IllegalArgumentException {
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException(
                    String.format("Invalid coordinates: latitude=%f, longitude=%f. " +
                                    "Latitude must be in [-90, 90], longitude must be in [-180, 180]",
                            latitude, longitude));
        }

        Optional<ZoneId> zoneId = engine.query(latitude, longitude);
        if (zoneId.isEmpty()) {
            throw new RuntimeException(String.format(
                    "Time zone not resolved for coordinates: latitude=%f, longitude=%f.",
                    latitude, longitude));
        }

        return zoneId.get();
    }
}
