package com.development.moon.dev.usercase.validation;

import com.development.moon.dev.model.UserResponses;
import com.development.moon.dev.service.UserResponsesService;
import com.development.moon.dev.usercase.exception.UserResponsesValidationException;
import com.development.moon.dev.usercase.port.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;

/**
 * UserResValidator is a component that provides validation methods for UserResponses entities.
 */
@Component
public class UserResValidator {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserResponsesService userResponsesService;

    /**
     * Constructs a UserResValidator with the specified PasswordEncoder.
     *
     * @param passwordEncoder the PasswordEncoder to use for password validation
     */
    public UserResValidator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Validates the UserResponses entity.
     *
     * @param userResponses the UserResponses entity to validate
     * @throws UserResponsesValidationException if the UserResponses entity is null or has invalid fields
     */
    public void validateCheckUserRes(final UserResponses userResponses) {
        if (userResponses == null) throw new UserResponsesValidationException("User Responses should not be null");
        if (isBlank(userResponses.getEmail())) throw new UserResponsesValidationException("Email should not be null");
        if (isBlank(userResponses.getName())) throw new UserResponsesValidationException("Name should not be null");
        if (isBlank(userResponses.getNumber())) throw new UserResponsesValidationException("Number should not be null");
        if (isBlank(userResponses.getMessage_res())) throw new UserResponsesValidationException("Message should not be null");
    }

    /**
     * Validates that the name of the UserResponses entity is unique.
     *
     * @param userResponses the UserResponses entity to validate
     * @throws UserResponsesValidationException if the name is already used
     */
    public void validateNameTheSameUserResponses(final UserResponses userResponses) {
        if (userResponsesService.findAll().stream().anyMatch(x ->
                userResponses.getName().equals(x.getName()))
        )
            throw new UserResponsesValidationException("This name is used.");
    }
}