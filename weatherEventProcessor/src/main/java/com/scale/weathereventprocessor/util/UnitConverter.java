package com.scale.weathereventprocessor.util;

public class UnitConverter {
    /**
     * Converts wind speed from imperial units (miles per hour, mph)
     * to metric units (meters per second, m/s).
     *
     * <p>Formula: {@code m/s = mph / 2.237}</p>
     *
     * @param imperialWindSpeed wind speed in miles per hour (mph)
     * @return wind speed in meters per second (m/s)
     */
    public static double windSpeedToMetric(double imperialWindSpeed) {
        return imperialWindSpeed / 2.237;
    }

    /**
     * Converts wind speed from metric units (meters per second, m/s)
     * to imperial units (miles per hour, mph).
     *
     * <p>Formula: {@code mph = m/s × 2.237}</p>
     *
     * @param metricWindSpeed wind speed in meters per second (m/s)
     * @return wind speed in miles per hour (mph)
     */
    public static double windSpeedToImperial(double metricWindSpeed) {
        return metricWindSpeed * 2.237;
    }

    /**
     * Converts temperature from imperial units (degrees Fahrenheit, °F)
     * to metric units (degrees Celsius, °C).
     *
     * <p>Formula: {@code °C = (°F - 32) × 5/9}</p>
     *
     * @param fahrenheitTemperature temperature in degrees Fahrenheit (°F)
     * @return temperature in degrees Celsius (°C)
     */
    public static double temperatureToCelsius(double fahrenheitTemperature) {
        return (fahrenheitTemperature - 32.0) * 5.0 / 9.0;
    }

    /**
     * Converts temperature from metric units (degrees Celsius, °C)
     * to imperial units (degrees Fahrenheit, °F).
     *
     * <p>Formula: {@code °F = (°C × 9/5) + 32}</p>
     *
     * @param celsiusTemperature temperature in degrees Celsius (°C)
     * @return temperature in degrees Fahrenheit (°F)
     */
    public static double temperatureToFahrenheit(double celsiusTemperature) {
        return (celsiusTemperature * 9.0 / 5.0) + 32.0;
    }
}