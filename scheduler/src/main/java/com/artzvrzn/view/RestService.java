package com.artzvrzn.view;

import com.artzvrzn.model.Operation;
import com.artzvrzn.model.OuterOperation;
import com.artzvrzn.model.ScheduledOperation;
import com.artzvrzn.view.api.IRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService implements IRestService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConversionService conversionService;
    @Value("${urls.service-account-service}")
    private String ACCOUNT_SERVICE_URL;

    @Override
    public void postScheduledOperation(ScheduledOperation operation) {
        String account = operation.getOperation().getAccount().toString();
        OuterOperation outer = conversionService.convert(operation.getOperation(), OuterOperation.class);
        HttpEntity<OuterOperation> httpEntity = new HttpEntity<>(outer);
        restTemplate.exchange(generateUrl(account), HttpMethod.POST, httpEntity, String.class);
        //TODO resolve exceptions
    }

    private String generateUrl(String accountId) {
        return ACCOUNT_SERVICE_URL + accountId + "/operation/";
    }
}
