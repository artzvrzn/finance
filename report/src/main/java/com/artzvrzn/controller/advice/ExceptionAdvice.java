package com.artzvrzn.controller.advice;

import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.validation.MultipleResponseError;
import com.artzvrzn.model.validation.SingleResponseError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    private final String ERROR = "error";

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationHandler(ValidationException exception) {
        if (exception.getErrorsAmount() == 0) {
            return new ResponseEntity<>(new SingleResponseError(ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new MultipleResponseError(ERROR, exception.getErrors()), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> invalidFormatHandler(InvalidFormatException exception) {
        return new ResponseEntity<>(new SingleResponseError(ERROR, "Some fields have wrong format"),
                HttpStatus.BAD_REQUEST);
    }
}
