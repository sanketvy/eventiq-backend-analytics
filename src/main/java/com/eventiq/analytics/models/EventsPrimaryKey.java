package com.eventiq.analytics.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@PrimaryKeyClass
@Data
@Builder
public class EventsPrimaryKey implements Serializable {

    @PrimaryKeyColumn(name = "projectId", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String projectId;

    @PrimaryKeyColumn(name = "eventType", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private String eventType;

    @PrimaryKeyColumn(name = "eventTimestamp", type = PrimaryKeyType.CLUSTERED, ordinal = 2)
    private long eventTimestamp;

    @PrimaryKeyColumn(name = "sessionId", type = PrimaryKeyType.CLUSTERED, ordinal = 3)
    private String sessionId;

    @PrimaryKeyColumn(name = "randomId", type = PrimaryKeyType.CLUSTERED, ordinal = 4)
    private String randomId;
}
