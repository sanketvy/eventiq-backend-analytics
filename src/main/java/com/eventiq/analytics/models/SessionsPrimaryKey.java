package com.eventiq.analytics.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@PrimaryKeyClass
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionsPrimaryKey implements Serializable {

    @PrimaryKeyColumn(name = "projectId", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String projectId;

    @PrimaryKeyColumn(name = "sessionId", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String sessionId;

    @PrimaryKeyColumn(name = "timestamp", type = PrimaryKeyType.CLUSTERED, ordinal = 2)
    private Long timestamp;

    @PrimaryKeyColumn(name = "randomId", type = PrimaryKeyType.CLUSTERED, ordinal = 3)
    private String randomId;

}
