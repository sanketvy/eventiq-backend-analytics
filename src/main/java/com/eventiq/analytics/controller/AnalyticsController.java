package com.eventiq.analytics.controller;

import com.eventiq.analytics.dto.AnalyticsRequest;
import com.eventiq.analytics.dto.DashboardResponse;
import com.eventiq.analytics.dto.SessionResponse;
import com.eventiq.analytics.models.Sessions;
import com.eventiq.analytics.service.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/v1")
public class AnalyticsController {

    AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService){
        this.analyticsService = analyticsService;
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
    public ResponseEntity<String> getAnalyticsByBrowser(@RequestBody AnalyticsRequest analyticsRequest){
        return ResponseEntity.status(HttpStatus.OK).body("Ok");
    }

    @PostMapping("/location")
    public ResponseEntity<String> getAnalyticsByLocation(@RequestBody AnalyticsRequest analyticsRequest){
        analyticsService.getLocationAnalytics();
        return ResponseEntity.status(HttpStatus.OK).body("Ok");
    }
}
