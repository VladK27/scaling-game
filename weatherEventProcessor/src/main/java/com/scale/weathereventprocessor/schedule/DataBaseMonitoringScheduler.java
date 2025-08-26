package com.scale.weathereventprocessor.schedule;

import com.scale.weathereventprocessor.service.impl.DatabaseMonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "app.db-monitoring.enabled", havingValue = "true")
public class DataBaseMonitoringScheduler {
    private static Long insertsInInterval = 0L;

    private final DatabaseMonitoringService databaseMonitoringService;

    @Autowired
    public DataBaseMonitoringScheduler(DatabaseMonitoringService databaseMonitoringService) {
        System.out.println("CERATED____");
        this.databaseMonitoringService = databaseMonitoringService;
    }

    @Scheduled(cron = "${app.db-monitoring.cron}")
    public void monitorDatabaseInserts() {
        long insertsNow = databaseMonitoringService.getAmountRowsInserted();
        log.info("[DB] Inserts: {}", insertsNow - insertsInInterval);
        insertsInInterval = insertsNow;
    }
}
