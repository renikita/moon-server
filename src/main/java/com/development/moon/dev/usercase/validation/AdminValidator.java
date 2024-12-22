package com.development.moon.dev.usercase.validation;

import com.development.moon.dev.model.Admin;
import com.development.moon.dev.model.Role;
import com.development.moon.dev.service.AdminService;
import com.development.moon.dev.usercase.exception.AdminValidationException;
import com.development.moon.dev.usercase.exception.UserResponsesValidationException;
import com.development.moon.dev.usercase.port.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.micrometer.common.util.StringUtils.isBlank;

/**
 * AdminValidator is a component that provides validation methods for Admin entities.
 */
@Component
public class AdminValidator {

    @Autowired
    AdminService adminService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an AdminValidator with the specified PasswordEncoder.
     *
     * @param passwordEncoder the PasswordEncoder to use for password validation
     */
    public AdminValidator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Validates the creation of an Admin entity.
     *
     * @param admin the Admin entity to validate
     * @throws AdminValidationException if the Admin entity is null or has invalid fields
     */
    public void validateCreateAdmin(final Admin admin) {
        if (admin == null) throw new AdminValidationException("Admin should not be null");
        if (isBlank(admin.getLogin())) throw new AdminValidationException("Login should not be null");
        if (isBlank(admin.getName())) throw new AdminValidationException("Name should not be null");
        if(isBlank(admin.getPassword())) throw new AdminValidationException("Password should be not null");
    }

    /**
     * Validates the creation of a Role entity.
     *
     * @param role the Role entity to validate
     * @throws AdminValidationException if the Role entity is null or has invalid fields
     */

    public void validateCreateRole(final Role role) {
        if (role == null) throw new AdminValidationException("Role should not be null");
        if (isBlank(role.getName())) throw new AdminValidationException("Name should not be null");
        if (isBlank(String.valueOf(role.getPermission()))) throw new AdminValidationException("Level should not be null");
    }



    public void validateNameTheSameRole(final Role role) {
        if (adminService.findAll().stream().anyMatch(x -> role.getName().equals(x.getName()))) {
            throw new UserResponsesValidationException("This name is used.");
        }
    }
    /**
     * Validates the password of an Admin entity.
     *
     * @param admin the Admin entity to validate
     * @param password the password to validate
     * @throws AdminValidationException if the password is incorrect
     */
    public void validatePasswordAdmin(final Admin admin, final String password) {
        if (!passwordEncoder.matches(password, admin.getPassword())) throw new AdminValidationException("Incorrect password");
    }

    /**
     * Validates that the login of the Admin entity is unique.
     *
     * @param admin the Admin entity to validate
     * @throws UserResponsesValidationException if the login is already used
     */
    public void validateNameTheSameAdmin(final Admin admin) {
        if (adminService.findAll().stream().anyMatch(x -> admin.getLogin().equals(x.getLogin()))) {
            throw new UserResponsesValidationException("This name is used.");
        }
    }
}