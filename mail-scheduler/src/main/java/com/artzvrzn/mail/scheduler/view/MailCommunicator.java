package com.artzvrzn.mail.scheduler.view;

import com.artzvrzn.mail.scheduler.model.MailParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MailCommunicator {

    @Value("${urls.mail-service}")
    private String mailServiceUrl;
    private final RestTemplate restTemplate;

    public MailCommunicator(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public void sendMailRequest(MailParams params) {
        String url = mailServiceUrl + "/report/send/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MailParams> httpEntity = new HttpEntity<>(params, headers);
        restTemplate.postForObject(url, httpEntity, Void.class);
    }
}
