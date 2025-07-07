package com.eventiq.analytics.repository;

import com.eventiq.analytics.models.Events;
import com.eventiq.analytics.models.EventsPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventsRepository extends CassandraRepository<Events, EventsPrimaryKey> {

    Optional<List<Events>> findAllByProjectId_ProjectIdAndProjectId_EventTimeGreaterThanEqual(String projectIdProjectId, LocalDateTime projectIdEventTimeIsGreaterThan);
}
