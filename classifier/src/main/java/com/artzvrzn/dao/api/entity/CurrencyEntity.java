package com.artzvrzn.dao.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Currency")
@Table(name = "currency", schema = "app")
public class CurrencyEntity extends BaseEntity {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
