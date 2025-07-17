package com.eventiq.analytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ClickhouseService {

    private final String clickhouseUrl = "http://localhost:8123/";

    @Autowired
    private RestTemplate restTemplate;

    public void fetchSessionCountsByCountryAndCity() {
        // SQL query
        String query = "SELECT country, city, count(*) AS sessionCount " +
                "FROM sessions GROUP BY country, city ORDER BY sessionCount DESC";

        // Full URL with default_format=JSON to get a clean response
        String fullUrl = clickhouseUrl + "?query=" + query + "&default_format=JSON";

        // Basic Auth
        String username = "default";
        String password = "default";

        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + encodedAuth;

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(MediaType.parseMediaTypes("application/json"));
        headers.set("Authorization", authHeader);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Make HTTP GET request
        ResponseEntity<String> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                request,
                String.class
        );

        // Print response
        System.out.println("ClickHouse Response:");
        System.out.println(response.getBody());
    }
}
