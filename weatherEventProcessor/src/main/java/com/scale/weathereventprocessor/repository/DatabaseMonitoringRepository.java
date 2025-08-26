package com.scale.weathereventprocessor.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseMonitoringRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseMonitoringRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long getAmountRowsInserted() {
        return jdbcTemplate.queryForObject("""
                SELECT n_tup_ins AS rows_inserted
                FROM pg_stat_all_tables
                WHERE schemaname = 'public'
                LIMIT 1
                """, Long.class );
    }
}
