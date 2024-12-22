package com.development.moon.dev.usercase;

import com.development.moon.dev.model.EventLog;
import com.development.moon.dev.repository.EventLogRepository;
import com.development.moon.dev.service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventLogdb implements EventLogService {

    @Autowired
    private EventLogRepository eventLogRepository;

    public void logEvent(String userId, String userName, String action, String details) {
        EventLog eventLog = new EventLog();
        eventLog.setUserId(userId);
        eventLog.setUserName(userName);
        eventLog.setAction(action);
        eventLog.setDetails(details);
        eventLog.setTimestamp(LocalDateTime.now());
        eventLogRepository.save(eventLog);
    }

    @Override
    public EventLog save(EventLog eventLog) {return eventLogRepository.save(eventLog);}

    @Override
    public EventLog findById(Integer id) {return eventLogRepository.findById(id).get();}

    @Override
    public List<EventLog> findAll() {return eventLogRepository.findAll();}

    @Override
    public boolean deleteById(Integer id) {eventLogRepository.deleteById(id); return eventLogRepository.existsById(id);}
}
