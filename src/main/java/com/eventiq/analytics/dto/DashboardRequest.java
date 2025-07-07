package com.eventiq.analytics.dto;

import lombok.Data;

@Data
public class DashboardRequest {

    private String projectId;

    private String timeframe;
}
