package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NationalityService {

    private static final String NATIONALIZE_API_URL = "https://api.nationalize.io/";

    public String getNationality(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = NATIONALIZE_API_URL + "?name=" + name;

        try {
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            // Handle the exception appropriately
            return null;
        }
    }
}

