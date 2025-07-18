package com.eventiq.analytics.controller;

import com.eventiq.analytics.dto.AnalyticsRequest;
import com.eventiq.analytics.dto.BrowserData;
import com.eventiq.analytics.dto.CountryData;
import com.eventiq.analytics.dto.SessionResponse;
import com.eventiq.analytics.service.AnalyticsService;
import com.eventiq.analytics.service.ClickhouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/v1")
public class AnalyticsController {

    AnalyticsService analyticsService;

    ClickhouseService clickhouseService;

    public AnalyticsController(AnalyticsService analyticsService, ClickhouseService clickhouseService){
        this.analyticsService = analyticsService;
        this.clickhouseService = clickhouseService;
    }

    @PostMapping("/session")
    public ResponseEntity<List<SessionResponse>> getSessionData(@RequestBody AnalyticsRequest analyticsRequest){
        return ResponseEntity.status(HttpStatus.OK).body(analyticsService.getSessionData(analyticsRequest));
    }

    @PostMapping("/session/{id}")
    public ResponseEntity<List<SessionResponse>> getSessionById(@RequestBody AnalyticsRequest analyticsRequest, @PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(analyticsService.getSessionDataById(id, analyticsRequest.getProjectId()));
    }

    @PostMapping("/device")
    public ResponseEntity<List<BrowserData>> getAnalyticsByBrowser(@RequestBody AnalyticsRequest analyticsRequest){
        return ResponseEntity.status(HttpStatus.OK).body(clickhouseService.fetchSessionCountsByBrowserAndDevice(analyticsRequest.getProjectId()));
    }

    @PostMapping("/location")
    public ResponseEntity<List<CountryData>> getAnalyticsByLocation(@RequestBody AnalyticsRequest analyticsRequest){
        return ResponseEntity.status(HttpStatus.OK).body(clickhouseService.fetchSessionCountsByCountryAndCity(analyticsRequest.getProjectId()));
    }
}
