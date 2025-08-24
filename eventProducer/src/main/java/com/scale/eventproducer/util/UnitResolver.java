package com.scale.eventproducer.util;

import java.util.Set;

public class UnitResolver {
    private static final Set<String> countriesWithImperialSystem = Set.of("United States", "Liberia", "Myanmar");

    public static boolean isUsesImperialSystem(String countryName) {
        return countriesWithImperialSystem.contains(countryName);
    }
}
