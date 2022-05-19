package com.artzvrzn.view;

import com.artzvrzn.model.report.ReportParam;
import com.artzvrzn.model.rest.Account;
import com.artzvrzn.view.api.IRestService;
import com.artzvrzn.model.rest.Operation;
import com.artzvrzn.model.rest.OperationsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RestService implements IRestService {

    @Value("${urls.account-service}")
    private String ACCOUNT_SERVICE_URL;
    @Value("${urls.classifier-service}")
    private String CLASSIFIER_SERVICE_URL;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Account> getAccounts(ReportParam reportParam) {
        List<Account> accounts = new ArrayList<>();
        for (UUID id: reportParam.getAccounts()) {
            accounts.add(getAccount(id));
        }
        return accounts;
    }

    @Override
    public List<Operation> getOperations(ReportParam reportParam) {
        List<Operation> operations = new ArrayList<>();
        String urlTemplate = ACCOUNT_SERVICE_URL + "/%s/?page=0&size=100"; //TODO resolve if total etc.
        for (UUID id: reportParam.getAccounts()) {
            OperationsPage page = restTemplate.getForObject(
                    String.format(urlTemplate, id), OperationsPage.class);
            operations.addAll(page.getContent());
        }
        return operations;
    }

    private Account getAccount(UUID id) {
        String accountUrl = ACCOUNT_SERVICE_URL + "/" + id;
        return restTemplate.getForObject(accountUrl, Account.class);
    }
}
