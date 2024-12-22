package com.development.moon.dev.service;


import com.development.moon.dev.model.EventLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventLogService {

    void logEvent(String userId, String userName, String action, String details);

    EventLog save(EventLog eventLog);

    EventLog findById(Integer id);

    public List<EventLog> findAll();

    boolean deleteById(Integer id);


}
