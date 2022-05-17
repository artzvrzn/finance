package com.artzvrzn.model;

public enum AccountType {

    CASH("Наличные деньги"),
    BANK_ACCOUNT("Счет в банке"),
    BANK_DEPOSIT("Депозит в банке");

    private final String description;
    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
