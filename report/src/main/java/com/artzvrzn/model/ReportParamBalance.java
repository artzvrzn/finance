package com.artzvrzn.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class ReportParamBalance implements Params {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UUID> accounts;

    public List<UUID> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<UUID> accounts) {
        this.accounts = accounts;
    }
}
