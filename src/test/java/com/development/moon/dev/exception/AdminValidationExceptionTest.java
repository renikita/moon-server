package com.development.moon.dev.exception;

import com.development.moon.dev.usercase.exception.AdminValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminValidationExceptionTest {

    @Test
    void constructorShouldSetMessage() {
        String message = "Validation failed";
        AdminValidationException exception = new AdminValidationException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorShouldHandleNullMessage() {
        AdminValidationException exception = new AdminValidationException(null);
        assertNull(exception.getMessage());
    }
}