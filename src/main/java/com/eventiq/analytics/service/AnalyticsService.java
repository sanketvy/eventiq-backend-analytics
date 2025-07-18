package com.eventiq.analytics.service;

import com.eventiq.analytics.dto.AnalyticsRequest;
import com.eventiq.analytics.dto.DashboardResponse;
import com.eventiq.analytics.dto.MetricResponse;
import com.eventiq.analytics.dto.SessionResponse;
import com.eventiq.analytics.models.Events;
import com.eventiq.analytics.models.EventsPrimaryKey;
import com.eventiq.analytics.models.Sessions;
import com.eventiq.analytics.repository.EventsRepository;
import com.eventiq.analytics.repository.SessionsRepository;
import com.eventiq.analytics.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.lang.constant.Constable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnalyticsService {

    EventsRepository eventsRepository;

    SessionsRepository sessionsRepository;

    ClickhouseService clickhouseService;

    ObjectMapper objectMapper;

    public AnalyticsService(EventsRepository eventsRepository, ClickhouseService clickhouseService, ObjectMapper objectMapper, SessionsRepository sessionsRepository){
        this.eventsRepository = eventsRepository;
        this.clickhouseService = clickhouseService;
        this.objectMapper = objectMapper;
        this.sessionsRepository = sessionsRepository;
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

    public List<SessionResponse> getSessionData(AnalyticsRequest analyticsRequest) {
        List<SessionResponse> response = new ArrayList<>();

        Optional<List<Events>> eventsList = eventsRepository.getSessions(analyticsRequest.getProjectId(), getTimeframe(analyticsRequest.getTimeframe()).toEpochSecond(ZoneOffset.UTC), "SESSION_START");

        if(eventsList.isPresent()){
            eventsList.get().forEach(event -> {

                SessionResponse sessionResponse = null;
                try {
                    sessionResponse = SessionResponse.builder()
                            .sessionId(event.getProjectId().getSessionId())
                            .city(event.getCity())
                            .country(event.getCountry())
                            .eventTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(event.getProjectId().getEventTimestamp()), ZoneOffset.UTC))
                            .metaData(objectMapper.readValue(event.getMetaData(), Map.class))
                            .userAgent(event.getUserAgent())
                            .agentType(event.getAgentType())
                            .build();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                response.add(sessionResponse);
            });
        }

        return response.reversed();
    }

    public List<SessionResponse> getSessionDataById(String id, String projectId) {

        List<SessionResponse> responseList = new ArrayList<>();
        Optional<List<Sessions>> session = sessionsRepository.findAllByKey_ProjectIdAndKey_SessionId(projectId, id);
        if(session.isPresent()){
            for (Sessions sessions : session.get()) {
                try {
                    SessionResponse response = SessionResponse.builder().sessionId(id).eventType(sessions.getType()).metaData(objectMapper.readValue(sessions.getMetadata(), Map.class)).eventTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(sessions.getKey().getTimestamp()), ZoneOffset.UTC)).build();
                    responseList.add(response);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return responseList;
    }
}
