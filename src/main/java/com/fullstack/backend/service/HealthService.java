package com.fullstack.backend.service;

import com.fullstack.backend.dto.HealthResponse;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

    private final MongoTemplate mongoTemplate;

    @Autowired
    public HealthService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public HealthResponse checkHealth() {
        HealthResponse.DatabaseStatus dbStatus;
        String overallStatus = "UP";
        String message = "All systems operational";

        try {
            Document commandResult = mongoTemplate.getDb().runCommand(new Document("ping", 1));
            String dbName = mongoTemplate.getDb().getName();
            dbStatus = new HealthResponse.DatabaseStatus("CONNECTED", dbName, "MongoDB connection successful (ping ok: " + commandResult.get("ok") + ")");
            log.info("MongoDB connectivity test successful for database: {}", dbName);
        } catch (Exception ex) {
            log.warn("MongoDB connectivity check failed: {}", ex.getMessage());
            overallStatus = "DEGRADED";
            message = "Application server running, but MongoDB connection failed";
            dbStatus = new HealthResponse.DatabaseStatus("UNREACHABLE", "unknown", ex.getMessage());
        }

        Map<String, Object> env = new HashMap<>();
        env.put("javaVersion", System.getProperty("java.version"));
        env.put("os", System.getProperty("os.name"));
        env.put("serverPort", 8008);

        return HealthResponse.of(overallStatus, message, dbStatus, env);
    }
}

