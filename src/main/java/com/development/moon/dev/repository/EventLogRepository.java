package com.development.moon.dev.repository;

import com.development.moon.dev.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Integer> {
}
