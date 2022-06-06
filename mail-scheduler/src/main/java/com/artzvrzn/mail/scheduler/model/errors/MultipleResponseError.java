package com.artzvrzn.mail.scheduler.model.errors;

import java.util.List;

public class MultipleResponseError {

    private final String logref;
    private final List<ValidationError> errors;

    public MultipleResponseError(List<ValidationError> errors) {
        this.logref = "structured_error";
        this.errors = errors;
    }

    public String getLogref() {
        return logref;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
