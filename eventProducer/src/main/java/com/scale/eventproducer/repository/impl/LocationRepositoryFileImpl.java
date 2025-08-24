package com.scale.eventproducer.repository.impl;

import com.scale.eventproducer.model.DeviceLocation;
import com.scale.eventproducer.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LocationRepositoryFileImpl implements LocationRepository {
    private final String dataFileLocation;
    private List<DeviceLocation> locationList;

    @Autowired
    public LocationRepositoryFileImpl(@Value("${app.datafile}") String dataFileLocation) {
        this.dataFileLocation = dataFileLocation;
    }

    @Override
    public List<DeviceLocation> loadLocations() {
        if (locationList == null || locationList.isEmpty()) {
            log.info("Start initializing locations data");
            long before = System.currentTimeMillis();
            this.locationList = readLocationsFromCsv();
            long after = System.currentTimeMillis();
            log.info("Successfully loaded {} locations in {}ms", this.locationList.size(), after - before);
        }

        return locationList;
    }

    private List<DeviceLocation> readLocationsFromCsv() {
        String cityHeader = "ASCII Name";
        String countryHeader = "Country name EN";
        String coordinatesHeader = "Coordinates";

        try (Reader in = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(this.dataFileLocation))) {
            CSVParser parser = CSVFormat.DEFAULT.builder()
                    .setDelimiter(';')
                    .setHeader(cityHeader, countryHeader, coordinatesHeader)
                    .get()
                    .parse(in);
            List<DeviceLocation> deviceLocations = new ArrayList<>((int) parser.getCurrentLineNumber());
            List<CSVRecord> records = parser.getRecords();

            for (CSVRecord record : records) {
                String[] coordinates = record.get(coordinatesHeader).split(", ");
                double latitude = Double.parseDouble(coordinates[0]);
                double longitude = Double.parseDouble(coordinates[1]);
                deviceLocations.add(new DeviceLocation(
                        record.get(cityHeader),
                        record.get(countryHeader),
                        longitude,
                        latitude
                ));
            }

            return deviceLocations;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
