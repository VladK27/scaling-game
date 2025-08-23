package com.scale.weathereventprocessor.repository;

import com.scale.weathereventprocessor.model.DeviceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceEventRepository extends JpaRepository<DeviceEvent, Long> {

}
