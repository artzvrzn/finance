package com.artzvrzn.mail.scheduler.controller.advice;

import com.artzvrzn.mail.scheduler.exception.ValidationException;
import com.artzvrzn.mail.scheduler.model.errors.MultipleResponseError;
import com.artzvrzn.mail.scheduler.model.errors.SingleResponseError;
import com.artzvrzn.mail.scheduler.model.errors.ValidationError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.format.DateTimeParseException;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private ObjectMapper mapper;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationHandler(ValidationException exception) {
        if (exception.getErrorsAmount() == 0) {
            return new ResponseEntity<>(new SingleResponseError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
        if (exception.getErrorsAmount() == 1) {
            ValidationError error = exception.getErrors().get(0);
            String message = String.format("%s %s", error.getField(), error.getMessage());
            return new ResponseEntity<>(new SingleResponseError(message), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(new MultipleResponseError(exception.getErrors()), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler({InvalidFormatException.class, DateTimeParseException.class})
    public ResponseEntity<?> invalidFormatHandler(InvalidFormatException exception) {
        return new ResponseEntity<>(new SingleResponseError("Some fields have wrong format"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<?> requestException(HttpStatusCodeException exception) throws JsonProcessingException {
        try {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.readValue(exception.getResponseBodyAsString(), Map.class));
        } catch (JsonProcessingException exc) {
            return new ResponseEntity<>(
                    new SingleResponseError("cannot create report with passed parameters"), HttpStatus.BAD_REQUEST);
        }
    }
}
