package com.eventiq.analytics.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
public class SessionResponse {

    private String sessionId;

    private LocalDateTime eventTime;

    private String idAddress;

    private String country;

    private String city;

    private String userAgent;

    private String agentType;

    private String eventType;

    private Map<String, Object> metaData;
}
