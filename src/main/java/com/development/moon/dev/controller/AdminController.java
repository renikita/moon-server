package com.development.moon.dev.controller;

import com.development.moon.dev.model.Admin;
import com.development.moon.dev.model.Role;
import com.development.moon.dev.service.AdminService;
import com.development.moon.dev.service.RoleService;
import com.development.moon.dev.usercase.EventLogdb;
import com.development.moon.dev.usercase.port.PasswordEncoder;
import com.development.moon.dev.usercase.validation.AdminValidator;
import com.development.moon.dev.util.JSONutil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AdminController is a REST controller that handles HTTP requests for managing Admin entities.
 */
@RestController
@RequestMapping("/adminpage")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;

    @Autowired
    AdminValidator adminValidator;
    @Autowired
    private EventLogdb eventLogdb;
    @Autowired
    JSONutil jsonutil;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for AdminController.
     *
     * @param passwordEncoder the PasswordEncoder to use for encoding passwords
     */
    public AdminController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new Admin entity.
     *
     * @param admin the Admin entity to save
     * @return the saved Admin entity
     */
    @PostMapping("/admin")
    Admin adminSave(@RequestBody Admin admin, HttpServletRequest request){
        HttpSession session = request.getSession(false);
       Admin whoAdmin = adminService.findById(Integer.valueOf((String) session.getAttribute("userId")));

      adminValidator.validateCreateAdmin(admin);
      adminValidator.validateNameTheSameAdmin(admin);
      admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        Map<String, String> eventDetails = new LinkedHashMap<>();
        eventDetails.put("User-Agent", request.getHeader("User-Agent"));
        eventDetails.put("RemoteAddr", request.getRemoteAddr());
        eventDetails.put("Device", request.getHeader("User-Agent").contains("Mobi") ? "Mobile" : "Desktop");
        eventDetails.put("Created by", whoAdmin.getName());
        eventDetails.put("Create ->", admin.getName());


            eventLogdb.logEvent(String.valueOf(whoAdmin.getId()), whoAdmin.getName(), "created admin", jsonutil.toJSON(eventDetails));

        return adminService.save(admin);
    }


    /**
     * Retrieves all Admin entities.
     *
     * @return a list of all Admin entities
     */
    @GetMapping("/admins")
    List<Admin> getAllAdmins(){
        return adminService.findAll();
    }

    /**
     * Retrieves the ID of the currently logged in Admin.
     *
     * @param request the HTTP request
     * @return the ID of the currently logged in Admin
     */

    @GetMapping("/currentadmin")
    Integer getCurrentAdmin(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Integer id = Integer.valueOf((String) session.getAttribute("userId"));;
        return id;
    }
    /**
     * Retrieves an Admin entity by its ID.
     *
     * @param id the ID of the Admin entity to retrieve
     * @return the retrieved Admin entity
     */
    @GetMapping("/admin/{id}")
    Admin getAdminById(@PathVariable Integer id){
        Admin admin = adminService.findById(id);
        adminValidator.validateCreateAdmin(admin);
        return admin;
    }

    /**
     * Updates an existing Admin entity by its ID.
     *
     * @param admin the Admin entity with updated information
     * @param id the ID of the Admin entity to update
     * @return the updated Admin entity
     */
    @PutMapping("/admin/{id}")
    Admin updateAdminById(@RequestBody Admin admin, @PathVariable Integer id, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Admin whoAdmin = adminService.findById(Integer.valueOf((String) session.getAttribute("userId")));

        Admin UpdateAdmin = adminService.findById(id);
        adminValidator.validateCreateAdmin(UpdateAdmin);
        UpdateAdmin.setName(admin.getName() == null || admin.getName().isEmpty() ? UpdateAdmin.getName() : admin.getName());
        UpdateAdmin.setRole(admin.getRole() == null ? UpdateAdmin.getRole() : admin.getRole());
        UpdateAdmin.setLogin(admin.getLogin() == null || admin.getLogin().isEmpty() ? UpdateAdmin.getLogin() : admin.getLogin());
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            UpdateAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        Map<String, String> eventDetails = new LinkedHashMap<>();
        eventDetails.put("User-Agent", request.getHeader("User-Agent"));
        eventDetails.put("RemoteAddr", request.getRemoteAddr());
        eventDetails.put("Device", request.getHeader("User-Agent").contains("Mobi") ? "Mobile" : "Desktop");
        eventDetails.put("Updated by", whoAdmin.getName());
        eventDetails.put("Update ->", UpdateAdmin.getName());
        eventDetails.put("Update login: ", UpdateAdmin.getLogin());
        eventDetails.put("Update role: ", UpdateAdmin.getRole().getName());


        eventLogdb.logEvent(String.valueOf(whoAdmin.getId()), whoAdmin.getName(), "updated admin", jsonutil.toJSON(eventDetails));
        return adminService.save(UpdateAdmin);
    }


    /**
     * Deletes an Admin entity by its ID.
     *
     * @param id the ID of the Admin entity to delete
     * @return a success message if the deletion was successful, otherwise an error message
     */
    @DeleteMapping("/admin/{id}")
    String deleteAdminById(@PathVariable Integer id, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Admin admin = adminService.findById(id);
        Admin whoAdmin = adminService.findById(Integer.valueOf((String) session.getAttribute("userId")));
        adminValidator.validateCreateAdmin(admin);


        // Logs the event of deleting an Admin entity
        Map<String, String> eventDetails = new LinkedHashMap<>();
        eventDetails.put("User-Agent", request.getHeader("User-Agent"));
        eventDetails.put("RemoteAddr", request.getRemoteAddr());
        eventDetails.put("Device", request.getHeader("User-Agent").contains("Mobi") ? "Mobile" : "Desktop");
        eventDetails.put("Deleted by", whoAdmin.getName());
        eventDetails.put("Deleted ->", admin.getName());

        eventLogdb.logEvent(String.valueOf(whoAdmin.getId()), whoAdmin.getName(), "delete admin", jsonutil.toJSON(eventDetails));

        return adminService.deleteById(id) ? "Success!" : "Deleting not completed.";
    }

    /**
     * Saves a new Role entity.
     *
     * @param role the Role entity to save
     * @return the saved Role entity
     */

    //    ROLES

    @PostMapping("/role")
    Role roleSave(@RequestBody Role role, HttpServletRequest request) {
       HttpSession session = request.getSession(false);
       Admin whoAdmin = adminService.findById(Integer.valueOf((String) session.getAttribute("userId")));

        adminValidator.validateCreateRole(role);
        adminValidator.validateNameTheSameRole(role);


        Map<String, String> eventDetails = new LinkedHashMap<>();
        eventDetails.put("User-Agent", request.getHeader("User-Agent"));
        eventDetails.put("RemoteAddr", request.getRemoteAddr());
        eventDetails.put("Device", request.getHeader("User-Agent").contains("Mobi") ? "Mobile" : "Desktop");
        eventDetails.put("Created by", whoAdmin.getName());

        eventLogdb.logEvent(String.valueOf(whoAdmin.getId()), whoAdmin.getName(), "created role", jsonutil.toJSON(eventDetails));

        return roleService.save(role);
    }




    /**
     * Retrieves all Role entities.
     *
     * @return a list of all Role entities
     */
    @GetMapping("/roles")
    List<Role> getAllRoles(){
        return roleService.findAll();
    }

    /**
     * Retrieves a Role entity by its ID.
     *
     * @param id the ID of the Role entity to retrieve
     * @return the retrieved Role entity
     */
    @PutMapping("/role/{id}")
    Role updateRoleById(@RequestBody Role role, @PathVariable Integer id){
        Role UpdateRole = roleService.findById(id);
        adminValidator.validateCreateRole(UpdateRole);
        UpdateRole.setName(role.getName());
        UpdateRole.setPermission(role.getPermission());
        return roleService.save(UpdateRole);
    }

    /**
     * Deletes a Role entity by its ID.
     *
     * @param id the ID of the Role entity to delete
     * @return a success message if the deletion was successful, otherwise an error message
     */
    @DeleteMapping("/role/{id}")
    String deleteRoleById(@RequestBody Role role, @PathVariable Integer id){
        Role UpdateRole = roleService.findById(id);
        adminValidator.validateCreateRole(UpdateRole);
        return roleService.deleteById(id) ? "Success!" : "Deleting not completed.";
    }
}