package com.development.moon.dev.controller;


import com.development.moon.dev.model.EventLog;
import com.development.moon.dev.repository.EventLogRepository;
import com.development.moon.dev.service.AdminService;
import com.development.moon.dev.service.EventLogService;
import com.development.moon.dev.service.UserResponsesService;
import com.development.moon.dev.usercase.EventLogdb;
import com.development.moon.dev.usercase.validation.UserResValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventlog")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class EventLogController {

    @Autowired
    private EventLogService eventLogService;


    @Autowired
    private EventLogRepository eventLogRepository;

    @GetMapping("/all")
    public List<EventLog> getAllEventLogs() {
        return eventLogRepository.findAll();
    }
}
