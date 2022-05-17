package com.artzvrzn.dao.api.entity;

import com.artzvrzn.model.AccountType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "accounts", schema = "finance")
public class AccountEntity extends BaseEntity {
    private String title;
    private String description;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private BalanceEntity balance;
    private AccountType type;
    private UUID currency;

    public BalanceEntity getBalance() {
        return balance;
    }

    public void setBalance(BalanceEntity balance) {
        this.balance = balance;
    }

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
