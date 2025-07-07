package com.eventiq.analytics.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Map;

@Table("events")
@Data
@Builder
public class Events {

    @PrimaryKey
    private EventsPrimaryKey projectId;

    private String type;

    private String metaData;
}
