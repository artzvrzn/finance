package com.artzvrzn.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Account {

    @JsonProperty("uuid")
    private UUID id;
    @JsonProperty("balance")
    private long balance;

    @JsonProperty("currency")
    private UUID currency;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }
}
