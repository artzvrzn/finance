package com.artzvrzn.model;

import com.artzvrzn.util.serializer.DecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

public class Operation extends BaseDTO {

    private long date;
    private String description;
    private UUID category;
    @JsonSerialize(using = DecimalSerializer.class)
    private double value;
    private UUID currency;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }
}
