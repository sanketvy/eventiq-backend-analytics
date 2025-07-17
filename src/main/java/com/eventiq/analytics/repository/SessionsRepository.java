package com.eventiq.analytics.repository;

import com.eventiq.analytics.models.Sessions;
import com.eventiq.analytics.models.SessionsPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionsRepository extends CassandraRepository<Sessions, SessionsPrimaryKey> {
    Optional<List<Sessions>> findAllByKey_ProjectIdAndKey_SessionId(String keyProjectId, String keySessionId);
}
