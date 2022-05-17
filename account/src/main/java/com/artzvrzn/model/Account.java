package com.artzvrzn.model;

import com.artzvrzn.util.serializer.DecimalSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

public class Account extends BaseDTO {

    private String title;
    private String description;
    @JsonSerialize(using = DecimalSerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double balance;
    private AccountType type;
    private UUID currency;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }
}
