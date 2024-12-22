package com.development.moon.dev.usercase;

import com.development.moon.dev.model.Admin;
import com.development.moon.dev.model.Role;
import com.development.moon.dev.service.AdminService;
import com.development.moon.dev.usercase.exception.AdminValidationException;
import com.development.moon.dev.usercase.exception.UserResponsesValidationException;
import com.development.moon.dev.usercase.port.PasswordEncoder;
import com.development.moon.dev.usercase.validation.AdminValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminValidatorTest {

    private AdminService adminService;
    private PasswordEncoder passwordEncoder;
    private AdminValidator adminValidator;

    @BeforeEach
    void setUp() {
        adminService = mock(AdminService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        adminValidator = new AdminValidator(passwordEncoder);

    }

    @Test
    void validateCreateAdminShouldThrowExceptionWhenAdminIsNull() {
        assertThrows(AdminValidationException.class, () -> adminValidator.validateCreateAdmin(null));
    }

    @Test
    void validateCreateAdminShouldThrowExceptionWhenLoginIsBlank() {
        Admin admin = new Admin();
        admin.setLogin("");
        assertThrows(AdminValidationException.class, () -> adminValidator.validateCreateAdmin(admin));
    }

    @Test
    void validateCreateAdminShouldThrowExceptionWhenNameIsBlank() {
        Admin admin = new Admin();
        admin.setName("");
        assertThrows(AdminValidationException.class, () -> adminValidator.validateCreateAdmin(admin));
    }

    @Test
    void validateCreateAdminShouldPassWhenAdminIsValid() {
        Admin admin = new Admin();
        admin.setLogin("validLogin");
        admin.setName("validName");
        admin.setPassword('1' + "validPassword");
        assertDoesNotThrow(() -> adminValidator.validateCreateAdmin(admin));
    }

    @Test
    void validateCreateRoleShouldThrowExceptionWhenRoleIsNull() {
        assertThrows(AdminValidationException.class, () -> adminValidator.validateCreateRole(null));
    }

    @Test
    void validateCreateRoleShouldThrowExceptionWhenNameIsBlank() {
        Role role = new Role();
        role.setName("");
        assertThrows(AdminValidationException.class, () -> adminValidator.validateCreateRole(role));
    }

    @Test
    void validateCreateRoleShouldThrowExceptionWhenPermissionIsBlank() {
        Role role = new Role();
        role.setPermission(null);
        assertThrows(AdminValidationException.class, () -> adminValidator.validateCreateRole(role));
    }

    @Test
    void validateCreateRoleShouldPassWhenRoleIsValid() {
        Role role = new Role();
        role.setName("validName");
        when(adminService.findAll()).thenReturn(List.of(new Admin()));
        assertDoesNotThrow(() -> adminValidator.validateCreateRole(role));
    }

    @Test
    void validatePasswordAdminShouldThrowExceptionWhenPasswordIsIncorrect() {
        Admin admin = new Admin();
        admin.setPassword("encodedPassword");
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);
        assertThrows(AdminValidationException.class, () -> adminValidator.validatePasswordAdmin(admin, "wrongPassword"));
    }

    @Test
    void validatePasswordAdminShouldPassWhenPasswordIsCorrect() {
        Admin admin = new Admin();
        admin.setPassword("encodedPassword");
        when(passwordEncoder.matches("correctPassword", "encodedPassword")).thenReturn(true);
        assertDoesNotThrow(() -> adminValidator.validatePasswordAdmin(admin, "correctPassword"));
    }

    @Test
    void validateNameTheSameAdminShouldThrowExceptionWhenLoginIsNotUnique() {
        Admin admin = new Admin();
        admin.setLogin("duplicateLogin");
        when(adminService.findAll()).thenReturn(List.of(admin));
        assertThrows(UserResponsesValidationException.class, () -> adminValidator.validateNameTheSameAdmin(admin));
    }

    @Test
    void validateNameTheSameAdminShouldPassWhenLoginIsUnique() {
        Admin admin = new Admin();
        admin.setLogin("uniqueLogin");
        when(adminService.findAll()).thenReturn(List.of());
        assertDoesNotThrow(() -> adminValidator.validateNameTheSameAdmin(admin));
    }

    @Test
    void validateNameTheSameRoleShouldThrowExceptionWhenNameIsNotUnique() {
        Role role = new Role();
        role.setName("duplicateName");
        when(adminService.findAll()).thenReturn(List.of(new Admin()));
        assertThrows(UserResponsesValidationException.class, () -> adminValidator.validateNameTheSameRole(role));
    }

    @Test
    void validateNameTheSameRoleShouldPassWhenNameIsUnique() {
        Role role = new Role();
        role.setName("uniqueName");
        when(adminService.findAll()).thenReturn(List.of());
        assertDoesNotThrow(() -> adminValidator.validateNameTheSameRole(role));
    }
}