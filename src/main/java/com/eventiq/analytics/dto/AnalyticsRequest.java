package com.eventiq.analytics.dto;

import lombok.Data;

@Data
public class AnalyticsRequest {

    private String projectId;

    private String timeframe;
}
