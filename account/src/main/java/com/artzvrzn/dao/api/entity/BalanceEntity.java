package com.artzvrzn.dao.api.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "balances", schema = "finance")
public class BalanceEntity {

    @Id
    @Column(name = "account_id", updatable = false)
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private AccountEntity account;
    private double value;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
