package com.eventiq.analytics.controller;

import com.eventiq.analytics.dto.DashboardRequest;
import com.eventiq.analytics.dto.DashboardResponse;
import com.eventiq.analytics.service.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService){
        this.analyticsService = analyticsService;
    }

    @PostMapping("/dashboard")
    public ResponseEntity<DashboardResponse> dashboardAnalytics(@AuthenticationPrincipal Jwt jwt, @RequestBody DashboardRequest dashboardRequest){
        return ResponseEntity.status(HttpStatus.OK).body(analyticsService.computeDashboardAnalytics(jwt, dashboardRequest));
    }
}
