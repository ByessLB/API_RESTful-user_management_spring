package com.learn.api.user_management_2.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.learn.api.user_management_2.user.exceptions.ErrorDetails;
import com.learn.api.user_management_2.user.exceptions.InvalidEmailException;
import com.learn.api.user_management_2.user.exceptions.InvalidGenderException;
import com.learn.api.user_management_2.user.exceptions.InvalidLoginException;
import com.learn.api.user_management_2.user.exceptions.InvalidPermissionDataException;
import com.learn.api.user_management_2.user.exceptions.InvalidRoleDataException;
import com.learn.api.user_management_2.user.exceptions.InvalidRoleIdentifierException;
import com.learn.api.user_management_2.user.exceptions.InvalidUserDataException;
import com.learn.api.user_management_2.user.exceptions.InvalidUserIdentifierException;
import com.learn.api.user_management_2.user.exceptions.InvalidUsernameException;
import com.learn.api.user_management_2.user.exceptions.PermissionInUseException;
import com.learn.api.user_management_2.user.exceptions.PermissionNotFoundException;
import com.learn.api.user_management_2.user.exceptions.RoleInUseException;
import com.learn.api.user_management_2.user.exceptions.RoleNotFoundException;
import com.learn.api.user_management_2.user.exceptions.UserIsSecuredException;
import com.learn.api.user_management_2.user.exceptions.UserNotFoundException;

/** Handles the exceptions globally in this API */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        InvalidEmailException.class,
        InvalidGenderException.class,
        InvalidUserDataException.class,
        InvalidUserIdentifierException.class,
        InvalidRoleDataException.class,
        InvalidRoleIdentifierException.class,
        InvalidUsernameException.class,
        InvalidLoginException.class,
        InvalidPermissionDataException.class,
        RoleInUseException.class,
        PermissionInUseException.class
    })
    public ResponseEntity<ErrorDetails> handleAsBadRequest(RuntimeException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        RoleNotFoundException.class,
        UserNotFoundException.class,
        UserIsSecuredException.class,
        PermissionNotFoundException.class
    })
    public ResponseEntity<ErrorDetails> handleAsNotFound(RuntimeException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
