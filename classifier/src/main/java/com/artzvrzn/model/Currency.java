package com.artzvrzn.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"uuid", "dt_created", "dt_updated", "title", "description"})
public class Currency extends BaseDTO {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
