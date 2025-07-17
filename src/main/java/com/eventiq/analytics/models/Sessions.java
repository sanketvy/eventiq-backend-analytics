package com.eventiq.analytics.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;

@Table("sessions")
@Data
@Builder
public class Sessions {

    @PrimaryKey
    private SessionsPrimaryKey key;

    private String type;

    private String metadata;
}
