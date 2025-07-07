package com.eventiq.analytics.service;

import com.eventiq.analytics.dto.DashboardRequest;
import com.eventiq.analytics.dto.DashboardResponse;
import com.eventiq.analytics.dto.MetricResponse;
import com.eventiq.analytics.models.Events;
import com.eventiq.analytics.repository.EventsRepository;
import com.eventiq.analytics.utils.Constants;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.lang.constant.Constable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsService {

    EventsRepository eventsRepository;

    public AnalyticsService(EventsRepository eventsRepository){
        this.eventsRepository = eventsRepository;
    }

    public DashboardResponse computeDashboardAnalytics(Jwt jwt, DashboardRequest dashboardRequest) {
        DashboardResponse dashboardResponse = new DashboardResponse();
        MetricResponse metricResponse = new MetricResponse();
        LocalDateTime timeframe = getTimeframe(dashboardRequest.getTimeframe());

        Optional<List<Events>> eventsList = eventsRepository.findAllByProjectId_ProjectIdAndProjectId_EventTimeGreaterThanEqual(dashboardRequest.getProjectId(), timeframe);

        System.out.println(dashboardRequest.getProjectId() + " " +dashboardRequest.getTimeframe());
        if (eventsList.isPresent()){
            System.out.println(eventsList.get().toString());
            metricResponse.setEventsCount((long) eventsList.get().size());
        }

        dashboardResponse.setMetrics(metricResponse);
        return dashboardResponse;
    }

    private LocalDateTime getTimeframe(String timeframe){
        if(timeframe.equals(Constants.FIFTEEN_MINUTES)){
            return LocalDateTime.now(ZoneId.of("UTC")).minusMinutes(15);
        } else if (timeframe.equals(Constants.ONE_HOUR)){
            return LocalDateTime.now(ZoneId.of("UTC")).minusHours(1);
        } else if (timeframe.equals(Constants.ONE_DAY)){
            return LocalDateTime.now(ZoneId.of("UTC")).minusDays(1);
        } else if (timeframe.equals(Constants.ONE_WEEK)){
            return LocalDateTime.now(ZoneId.of("UTC")).minusWeeks(1);
        } else {
            throw new IllegalArgumentException("Invalid timeframe");
        }
    }
}
