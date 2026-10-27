package com.fullstack.backend.repository;

import com.fullstack.backend.model.AppStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppStatusRepository extends MongoRepository<AppStatus, String> {
    Optional<AppStatus> findByComponent(String component);
}
