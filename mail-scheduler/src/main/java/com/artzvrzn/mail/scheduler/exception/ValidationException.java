package com.artzvrzn.mail.scheduler.exception;

import com.artzvrzn.mail.scheduler.model.errors.ValidationError;

import java.util.Collections;
import java.util.List;

public class ValidationException extends IllegalArgumentException {

    private final List<ValidationError> errors;

    public ValidationException(String message) {
        super(message);
        errors = Collections.emptyList();
    }

    public ValidationException(List<ValidationError> errors) {
        super("structured_error");
        this.errors = errors;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        errors = Collections.emptyList();
    }

    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public int getErrorsAmount() {
        return errors.size();
    }


}
