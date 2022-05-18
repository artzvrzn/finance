package com.artzvrzn.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReportParamByDate implements Params {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UUID> accounts;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate from;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate to;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UUID> categories;

    public List<UUID> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<UUID> accounts) {
        this.accounts = accounts;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public List<UUID> getCategories() {
        return categories;
    }

    public void setCategories(List<UUID> categories) {
        this.categories = categories;
    }
}
