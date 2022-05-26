package com.artzvrzn.view;

import com.artzvrzn.model.*;
import com.artzvrzn.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

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

    public List<Operation> getOperations(UUID accountId, long from, long to, Collection<UUID> categories) {
        int elements = 100;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ACCOUNT_SERVICE_URL);
        uriBuilder
                .path("/" + accountId)
                .path("/operation/backend/report")
                .queryParam("page", 0)
                .queryParam("size", elements)
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("cat", categories);
        List<Operation> operations = new LinkedList<>();
        OperationsPage page = restTemplate.getForObject(uriBuilder.build().toString(), OperationsPage.class);
        if (page == null) {
            return Collections.emptyList();
        }
        System.out.println(page.getTotalPages());
        operations.addAll(page.getContent());
        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            for (int i = 1; i < totalPages; i++) {
                uriBuilder.replaceQueryParam("page", i);
                page = restTemplate.getForObject(uriBuilder.build().toString(), OperationsPage.class);
                operations.addAll(page.getContent());
            }
        }
        return operations;
    }

    public Account getAccount(UUID accountId) {
        String accountUrl = ACCOUNT_SERVICE_URL + "/" + accountId;
        return restTemplate.getForObject(accountUrl, Account.class);
    }

    public Currency readCurrency(UUID currencyId) {
        String currencyUrl = CLASSIFIER_SERVICE_URL + "/currency/" + currencyId;
        return restTemplate.getForObject(currencyUrl, Currency.class);
    }

    public Category readCategory(UUID categoryId) {
        String currencyUrl = CLASSIFIER_SERVICE_URL + "/operation/category/" + categoryId;
        return restTemplate.getForObject(currencyUrl, Category.class);
    }
}
