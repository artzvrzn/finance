package com.artzvrzn.model.report;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

public class ReportParamBalance extends ReportParam {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UUID> accounts;

    public List<UUID> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<UUID> accounts) {
        this.accounts = accounts;
    }
}
