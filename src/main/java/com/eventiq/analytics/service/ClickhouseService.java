package com.eventiq.analytics.service;

import com.eventiq.analytics.dto.BrowserData;
import com.eventiq.analytics.dto.CountryData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClickhouseService {

    private final String clickhouseUrl = "http://localhost:8123/";

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    public List<CountryData> fetchSessionCountsByCountryAndCity(String projectId) {
        String query = "SELECT country, city, count(*) AS sessionCount " +
                "FROM sessions " +
                "WHERE projectId = '" + projectId + "' " +
                "GROUP BY country, city " +
                "ORDER BY sessionCount DESC";
        String fullUrl = clickhouseUrl + "?query=" + query + "&default_format=JSON";

        String username = "default";
        String password = "default";
        String encodedAuth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            try {
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode data = root.get("data");

                Map<String, CountryData> countryMap = new LinkedHashMap<>();

                for (JsonNode row : data) {
                    String country = row.get("country").asText();
                    String city = row.get("city").asText();
                    int sessionCount = row.get("sessionCount").asInt();

                    countryMap.putIfAbsent(country, new CountryData(country));
                    countryMap.get(country).addCity(city, sessionCount);
                }

                return new ArrayList<>(countryMap.values());

            } catch (Exception e) {
                throw new RuntimeException("No Data Found");
            }
        }
        throw new RuntimeException("No Data Found");
    }

    public List<BrowserData> fetchSessionCountsByBrowserAndDevice(String projectId) {
        String query = "SELECT userAgent, agentType, count(*) AS sessionCount " +
                "FROM sessions " +
                "WHERE projectId = '" + projectId + "' " +
                "GROUP BY userAgent, agentType " +
                "ORDER BY sessionCount DESC";

        try {
            String fullUrl = clickhouseUrl + "?query=" + query + "&default_format=JSON";

            String username = "default";
            String password = "default";
            String encodedAuth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            headers.setAccept(MediaType.parseMediaTypes("application/json"));

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                try {
                    JsonNode root = mapper.readTree(response.getBody());
                    JsonNode data = root.get("data");

                    Map<String, BrowserData> browserMap = new LinkedHashMap<>();

                    for (JsonNode row : data) {
                        String browser = row.get("userAgent").asText();
                        String agent = row.get("agentType").asText();
                        int sessionCount = row.get("sessionCount").asInt();

                        browserMap.putIfAbsent(browser, new BrowserData(browser));
                        browserMap.get(browser).addDevice(agent, sessionCount);
                    }

                    return new ArrayList<>(browserMap.values());
                } catch (Exception e) {
                    throw new RuntimeException("No Data Found");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch browser sessions", e);
        }

        return Collections.emptyList();
    }

}
