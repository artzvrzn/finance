package com.artzvrzn.model.validation;

public class SingleResponseError {

    private final String logref;
    private final String message;

    public SingleResponseError(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public String getMessage() {
        return message;
    }

}
