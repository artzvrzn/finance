package com.artzvrzn.view.api;

import com.artzvrzn.model.report.ReportParam;
import com.artzvrzn.model.rest.Account;
import com.artzvrzn.model.rest.Operation;

import java.util.List;

public interface IRestService {

    List<Account> getAccounts(ReportParam reportParam);

    List<Operation> getOperations(ReportParam reportParam);

}
