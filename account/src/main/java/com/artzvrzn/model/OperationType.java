package com.artzvrzn.model;

public enum OperationType {

    SPEND("Расход"),
    RECEIVE("Приход");

    private final String description;

    OperationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
