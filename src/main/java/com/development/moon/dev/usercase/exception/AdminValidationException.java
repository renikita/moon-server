package com.development.moon.dev.usercase.exception;

import com.development.moon.dev.model.Admin;
import com.development.moon.dev.usercase.validation.AdminValidator;

public class AdminValidationException extends RuntimeException{

    public AdminValidationException(final String message){
        super(message);
    }

}
