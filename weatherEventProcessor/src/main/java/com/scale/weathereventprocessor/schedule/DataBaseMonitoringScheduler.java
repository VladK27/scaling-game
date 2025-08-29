package com.scale.weathereventprocessor.schedule;

import com.scale.weathereventprocessor.service.impl.DatabaseMonitoringService;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(name = "app.db-monitoring.enabled", havingValue = "true")
public class DataBaseMonitoringScheduler {
    private static Long insertsBefore = 0L;
    private static final List<Long> insertsLog = new ArrayList<>();

    private final DatabaseMonitoringService databaseMonitoringService;

    @Autowired
    public DataBaseMonitoringScheduler(DatabaseMonitoringService databaseMonitoringService) {
        this.databaseMonitoringService = databaseMonitoringService;
    }

    @PreDestroy
    public void destroy() {
        double averageInsertsPerInterval =
                ((double) insertsLog.stream().mapToLong(e -> e).sum()) / insertsLog.size();
        log.info("[DB] Average inserts: {}", averageInsertsPerInterval);
    }

    @Scheduled(cron = "${app.db-monitoring.cron}")
    public void monitorDatabaseInserts() {
        long insertsNow = databaseMonitoringService.getAmountRowsInserted();
        long insertsPerInterval = insertsNow - insertsBefore;
        log.info("[DB] Inserts: {}", insertsPerInterval);
        if (insertsPerInterval > 0 && insertsBefore != 0L) {
            insertsLog.add(insertsPerInterval);
        }
        insertsBefore = insertsNow;
    }
}
