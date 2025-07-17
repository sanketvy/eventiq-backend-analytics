package com.eventiq.analytics.repository;

import com.eventiq.analytics.models.Events;
import com.eventiq.analytics.models.EventsPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventsRepository extends CassandraRepository<Events, EventsPrimaryKey> {

    @Query("SELECT * FROM events WHERE \"projectId\" = ?0 AND \"eventTimestamp\" >= ?1 AND \"eventType\" = ?2")
    public Optional<List<Events>> getSessions(String projectId, long eventTimestamp, String eventType);
}
