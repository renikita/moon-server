package com.development.moon.dev.controller;

import com.development.moon.dev.model.Admin;
import com.development.moon.dev.model.EventLog;
import com.development.moon.dev.model.UserResponses;
import com.development.moon.dev.service.AdminService;
import com.development.moon.dev.service.UserResponsesService;
import com.development.moon.dev.usercase.EventLogdb;
import com.development.moon.dev.usercase.exception.UserResponsesValidationException;
import com.development.moon.dev.usercase.validation.UserResValidator;
import com.development.moon.dev.util.JSONutil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * UserResponsesController is a REST controller that handles HTTP requests for managing UserResponses entities.
 */
@RestController
@RequestMapping("/response")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class UserResponsesController {

    @Autowired
    private UserResponsesService userResponsesService;

    @Autowired
    UserResValidator userResValidator;

    @Autowired
    AdminService adminService;

    @Autowired
    JSONutil jsonutil;

    EventLog eventLog;

    @Autowired
    private EventLogdb eventLogdb;

    /**
     * Saves a new UserResponses entity.
     *
     * @param userResponses the UserResponses entity to save
     * @return the saved UserResponses entity
     */
    @PostMapping("/user")
    UserResponses UR(@RequestBody UserResponses userResponses, HttpServletRequest request){
        userResValidator.validateNameTheSameUserResponses(userResponses);
        userResponses.setResponse_time(new Date());


        Map<String, String> eventDetails = new LinkedHashMap<>();
        eventDetails.put("User-Agent", request.getHeader("User-Agent"));
        eventDetails.put("RemoteAddr", request.getRemoteAddr());
        eventDetails.put("Device", request.getHeader("User-Agent").contains("Mobi") ? "Mobile" : "Desktop");


        eventLogdb.logEvent(String.valueOf(userResponses.getId()), userResponses.getName(), "send form", jsonutil.toJSON(eventDetails));

        return userResponsesService.save(userResponses);
    }

    /**
     * Retrieves all UserResponses entities.
     *
     * @return a list of all UserResponses entities
     */
    @GetMapping("/users")
    List<UserResponses> getAllUserResponses(){
        return userResponsesService.findAll();
    }

    /**
     * Retrieves a UserResponses entity by its ID.
     *
     * @param id the ID of the UserResponses entity to retrieve
     * @return the retrieved UserResponses entity
     * @throws UserResponsesValidationException if the UserResponses entity is not found
     */
    @GetMapping("/user/{id}")
    UserResponses getUserResponsesById(@PathVariable Integer id) {
        UserResponses userResponses = userResponsesService.findById(id);
        if (userResponses == null) {
            throw new UserResponsesValidationException("User with id " + id + " not found");
        }
        return userResponses;
    }

    /**
     * Updates an existing UserResponses entity by its ID.
     *
     * @param userResponses the UserResponses entity with updated information
     * @param id the ID of the UserResponses entity to update
     * @return the updated UserResponses entity
     */
    @PutMapping("/user/{id}")
    UserResponses updateUserById(@RequestBody UserResponses userResponses, @PathVariable Integer id, HttpServletRequest request){
        HttpSession session = request.getSession();
        String userIdString = (String) session.getAttribute("userId");

        if (userIdString == null) {
            throw new IllegalArgumentException("Session expired or user not logged in.");
        }

        Admin whoAdmin = adminService.findById(Integer.valueOf(userIdString));



        UserResponses UpdateUserResponses = userResponsesService.findById(id);

        Map<String, String> eventDetails = new LinkedHashMap<>();
        eventDetails.put("User-Agent", request.getHeader("User-Agent"));
        eventDetails.put("RemoteAddr", request.getRemoteAddr());
        eventDetails.put("Device", request.getHeader("User-Agent").contains("Mobi") ? "Mobile" : "Desktop");
        eventDetails.put("Updated by", whoAdmin.getName());
        eventDetails.put("Update user`s name", userResponses.getName() + " -> " + UpdateUserResponses.getName());
        eventDetails.put("Update user`s email", userResponses.getEmail() + " -> " + UpdateUserResponses.getEmail());
        eventDetails.put("Update user`s number", userResponses.getNumber() + " -> " + UpdateUserResponses.getNumber());
        eventDetails.put("Update user`s message", userResponses.getMessage_res() + " -> " + UpdateUserResponses.getMessage_res());
        eventDetails.put("Update user`s response time", userResponses.getResponse_time() + " -> " + UpdateUserResponses.getResponse_time());
        eventDetails.put("Update user`s status", userResponses.getStatus() + " -> " + UpdateUserResponses.getStatus());

         userResValidator.validateCheckUserRes(UpdateUserResponses);
        UpdateUserResponses.setName(userResponses.getName());
        UpdateUserResponses.setEmail(userResponses.getEmail());
        UpdateUserResponses.setNumber(userResponses.getNumber());
        UpdateUserResponses.setMessage_res(userResponses.getMessage_res());
        UpdateUserResponses.setResponse_time(userResponses.getResponse_time());
        UpdateUserResponses.setStatus(userResponses.getStatus());





        eventLogdb.logEvent(String.valueOf(whoAdmin.getId()), whoAdmin.getName(), "update user response", jsonutil.toJSON(eventDetails));

        return userResponsesService.save(UpdateUserResponses);
    }

    /**
     * Deletes a UserResponses entity by its ID.
     *
     * @param id the ID of the UserResponses entity to delete
     * @return a success message if the deletion was successful, otherwise an error message
     * @throws UserResponsesValidationException if the UserResponses entity is not found
     */
    @DeleteMapping("user/{id}")
    String deleteUserResponsesById(@PathVariable Integer id, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Admin whoAdmin = adminService.findById(Integer.valueOf((String) session.getAttribute("userId")));
        UserResponses userResponses = userResponsesService.findById(id);
        if (userResponses == null){
            throw new UserResponsesValidationException(("User with id " + id + " not found"));
        }

        Map<String, String> eventDetails = new LinkedHashMap<>();
        eventDetails.put("User-Agent", request.getHeader("User-Agent"));
        eventDetails.put("RemoteAddr", request.getRemoteAddr());
        eventDetails.put("Device", request.getHeader("User-Agent").contains("Mobi") ? "Mobile" : "Desktop");
        eventDetails.put("Deleted by", whoAdmin.getName());
        eventDetails.put("Deleted ->", userResponses.getName());
        eventDetails.put("Deleted email", userResponses.getEmail());
        eventDetails.put("Deleted number", userResponses.getNumber());
        eventDetails.put("Deleted message", userResponses.getMessage_res());



        eventLogdb.logEvent(String.valueOf(whoAdmin.getId()), whoAdmin.getName(), "delete user response", jsonutil.toJSON(eventDetails));

        return userResponsesService.deleteById(id) ? "Deleting not completed." : "Success!";
    }
}