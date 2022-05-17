package com.artzvrzn.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class Main {

    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
        String url = "http://localhost:8082/classifier/currency/";
        String uuid = "4e2d5184-9b0f-4446-8e05-fc8de79a0591";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> http = new HttpEntity<>(headers);
        System.out.println(template.exchange(url + uuid, HttpMethod.GET, http, String.class));
    }
}
