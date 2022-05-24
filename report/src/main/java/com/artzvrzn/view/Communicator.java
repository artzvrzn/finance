package com.artzvrzn.view;

import com.artzvrzn.model.Account;
import com.artzvrzn.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Communicator {

    @Value("${urls.account-service}")
    private String ACCOUNT_SERVICE_URL;
    @Value("${urls.classifier-service}")
    private String CLASSIFIER_SERVICE_URL;
    @Autowired
    private RestTemplate restTemplate;

    public List<Account> getAccounts(List<UUID> ids) {
        List<Account> accounts = new ArrayList<>();
        for (UUID id: ids) {
            accounts.add(getAccount(id));
        }
        return accounts;
    }

    public Account getAccount(UUID accountId) {
        String accountUrl = ACCOUNT_SERVICE_URL + "/" + accountId;
        return restTemplate.getForObject(accountUrl, Account.class);
    }

    public Currency readCurrency(UUID currencyId) {
        String currencyUrl = CLASSIFIER_SERVICE_URL + "/currency/" + currencyId;
        return restTemplate.getForObject(currencyUrl, Currency.class);
    }
}
