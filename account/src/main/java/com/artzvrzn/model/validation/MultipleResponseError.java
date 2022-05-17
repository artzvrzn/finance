package com.artzvrzn.model.validation;

import java.util.List;

public class MultipleResponseError {

    private final String logref;
    private final List<ValidationError> errors;

    public MultipleResponseError(String logref, List<ValidationError> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public String getLogref() {
        return logref;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
