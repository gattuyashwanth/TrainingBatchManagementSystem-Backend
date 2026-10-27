package com.fullstack.backend.dto;

import java.time.Instant;
import java.util.Map;

public record HealthResponse(
        String status,
        String message,
        String timestamp,
        String service,
        DatabaseStatus database,
        Map<String, Object> environment
) {
    public record DatabaseStatus(
            String status,
            String databaseName,
            String details
    ) {}

    public static HealthResponse of(String status, String message, DatabaseStatus dbStatus, Map<String, Object> env) {
        return new HealthResponse(
                status,
                message,
                Instant.now().toString(),
                "Spring Boot REST Backend",
                dbStatus,
                env
        );
    }
}
