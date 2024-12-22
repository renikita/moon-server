package com.development.moon.dev.controller;

import com.development.moon.dev.model.Admin;
import com.development.moon.dev.model.EventLog;
import com.development.moon.dev.model.dto.LoginRequest;
import com.development.moon.dev.service.AdminService;
import com.development.moon.dev.usercase.EventLogdb;
import com.development.moon.dev.usercase.validation.AdminValidator;
import com.development.moon.dev.util.JSONutil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * AuthController is a REST controller that handles HTTP requests for authentication.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    AdminValidator adminValidator;

    @Autowired
    JSONutil jsonutil;

    EventLog eventLog;
    @Autowired
    private EventLogdb eventLogdb;

    /**
     * Handles the login request.
     *
     * @param loginRequest the login request containing login and password
     * @return a success message if the login is successful
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> Login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Admin checkAdmin = adminService.findByReg(loginRequest.getLogin());

        Map<String, String> eventDetails = new LinkedHashMap<>();
        eventDetails.put("User-Agent", request.getHeader("User-Agent"));
        eventDetails.put("RemoteAddr", request.getRemoteAddr());
        eventDetails.put("Device", request.getHeader("User-Agent").contains("Mobi") ? "Mobile" : "Desktop");

        eventLogdb.logEvent(String.valueOf(checkAdmin.getId()), checkAdmin.getName(), "logged in", jsonutil.toJSON(eventDetails));

        adminValidator.validatePasswordAdmin(checkAdmin, loginRequest.getPassword());
        session.setAttribute("userId", String.valueOf(checkAdmin.getId()));
        session.setMaxInactiveInterval(120 * 60);

        Map<String, String> response = new HashMap<>();
        response.put("sessionId", session.getId());
        return response;
    }
}