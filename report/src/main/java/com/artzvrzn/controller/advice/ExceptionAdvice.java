package com.artzvrzn.controller.advice;

import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.errors.MultipleResponseError;
import com.artzvrzn.model.errors.SingleResponseError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationHandler(ValidationException exception) {
        if (exception.getErrorsAmount() == 0) {
            return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new MultipleResponseError(exception.getErrors()), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler({InvalidFormatException.class, MismatchedInputException.class, DateTimeParseException.class})
    public ResponseEntity<?> invalidFormatHandler(InvalidFormatException exception) {

        return new ResponseEntity<>(new SingleResponseError(exception.getTargetType().getSimpleName()),
                HttpStatus.BAD_REQUEST);
    }
}
