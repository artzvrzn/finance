package com.artzvrzn.view;

import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.view.api.IClassifierValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class ClassifierValidationService implements IClassifierValidationService {

    private final RestTemplate restTemplate;
    @Value("${urls.classifier-service}")
    private String classifierURL;

    public ClassifierValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isCurrencyPresent(UUID id) {
        String url = classifierURL + "currency/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        try {
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            throw new ValidationException(String.format("Currency with id %s doesn't exist", id));
        }
        return true;
    }

    @Override
    public boolean isCategoryPresent(UUID id) {
        String url = classifierURL + "operation/category/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        try {
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            throw new ValidationException(String.format("Category with id %s doesn't exist", id));
        }
        return true;
    }
}
