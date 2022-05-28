package com.artzvrzn.exception;

import com.artzvrzn.model.errors.ValidationError;

import java.util.Collections;
import java.util.List;

public class ValidationException extends IllegalArgumentException {

    private final List<ValidationError> errors;

    public ValidationException(String message) {
        super(message);
        errors = Collections.emptyList();
    }

    public ValidationException(String message, List<ValidationError> errors) {
        super(message);
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
