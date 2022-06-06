package com.artzvrzn.mail.scheduler.model.errors;

public class SingleResponseError {

    private final String logref;
    private final String message;

    public SingleResponseError(String message) {
        this.logref = "error";
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public String getMessage() {
        return message;
    }

}
