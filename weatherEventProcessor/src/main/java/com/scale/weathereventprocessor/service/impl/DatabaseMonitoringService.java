package com.scale.weathereventprocessor.service.impl;

import com.scale.weathereventprocessor.repository.DatabaseMonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseMonitoringService {
    private final DatabaseMonitoringRepository databaseMonitoringRepository;

    @Autowired
    public DatabaseMonitoringService(DatabaseMonitoringRepository databaseMonitoringRepository) {
        this.databaseMonitoringRepository = databaseMonitoringRepository;
    }

    public Long getAmountRowsInserted() {
        return databaseMonitoringRepository.getAmountRowsInserted();
    }
}
